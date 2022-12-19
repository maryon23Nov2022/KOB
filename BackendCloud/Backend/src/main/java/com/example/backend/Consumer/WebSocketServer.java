package com.example.backend.Consumer;

import com.alibaba.fastjson.JSONObject;
import com.example.backend.Consumer.Utilities.Game;
import com.example.backend.Consumer.Utilities.JwtAuthentication;
import com.example.backend.Mapper.RecordMapper;
import com.example.backend.Mapper.UserMapper;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {

    public final static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private User user;
    private Session session = null;

    private static UserMapper userMapper;
    public static RecordMapper recordMapper;
    private static RestTemplate restTemplate;
    private Game game = null;
    private static final String addPlayerURL = "http://127.0.0.1:8100/player/add/";
    private static final String removePlayerURL = "http://127.0.0.1:8100/player/remove/";

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    @Autowired
    public void setRecordMapper(RecordMapper recordMapper){
        WebSocketServer.recordMapper = recordMapper;
    }
    @Autowired void setRestTemplate(RestTemplate restTemplate){
        WebSocketServer.restTemplate = restTemplate;
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        this.session = session;
        System.out.printf("Connected\n");
        Integer userId = JwtAuthentication.getUserId(token);
        this.user = userMapper.selectById(userId);

        if(this.user != null) {
            users.put(userId, this);
        } else{
            this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.printf("Disconnected\n");
        if(this.user != null){
            users.remove(this.user.getId());
        }
    }

    public static void startGame(Integer aId, Integer bId){
        User a = userMapper.selectById(aId), b = userMapper.selectById(bId);
        Game game = new Game(13, 14, 10, a.getId(), b.getId());
        game.createMap();
        if(users.get(a.getId()) != null)
            users.get(a.getId()).game = game;
        if(users.get(b.getId()) != null)
            users.get(b.getId()).game = game;
        game.start();

        JSONObject respGame = new JSONObject();
        respGame.put("a_id", game.getPlayerA().getId());
        respGame.put("a_sx", game.getPlayerA().getSx());
        respGame.put("a_sy", game.getPlayerA().getSy());
        respGame.put("b_id", game.getPlayerB().getId());
        respGame.put("b_sx", game.getPlayerB().getSx());
        respGame.put("b_sy", game.getPlayerB().getSy());
        respGame.put("map", game.getG());

        JSONObject respA = new JSONObject();
        respA.put("event", "start-matching");
        respA.put("opponent_username", b.getName());
        respA.put("opponent_photo", b.getPhoto());
        respA.put("game", respGame);
        if(users.get(a.getId()) != null)
            users.get(a.getId()).sendMessage(respA.toJSONString());

        JSONObject respB = new JSONObject();
        respB.put("event", "start-matching");
        respB.put("opponent_username", a.getName());
        respB.put("opponent_photo", a.getPhoto());
        respB.put("game", respGame);
        if(users.get(b.getId()) != null)
            users.get(b.getId()).sendMessage(respB.toJSONString());
    }

    public void startMatching(){
        System.out.println("Begin");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        data.add("rating", this.user.getRating().toString());
        restTemplate.postForObject(addPlayerURL, data, String.class);
    }

    public void stopMatching(){
        System.out.println("Stop");
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", this.user.getId().toString());
        restTemplate.postForObject(removePlayerURL, data, String.class);
    }

    private void move(int d){
        if(game.getPlayerA().getId() == user.getId()){
            game.setNextStepA(d);
        } else if(game.getPlayerB().getId() == user.getId()){
            game.setNextStepB(d);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
        System.out.println("Message received");
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)){
            startMatching();
        } else if("stop-matching".equals(event)){
            stopMatching();
        } else if("move".equals(event)){
            move(data.getInteger("direction"));
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message){
        synchronized(this.session){
            try{
                this.session.getBasicRemote().sendText(message);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}