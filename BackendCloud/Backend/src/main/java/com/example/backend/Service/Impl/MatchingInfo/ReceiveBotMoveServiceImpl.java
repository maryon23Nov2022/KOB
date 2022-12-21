package com.example.backend.Service.Impl.MatchingInfo;

import com.example.backend.Consumer.Utilities.Game;
import com.example.backend.Consumer.WebSocketServer;
import com.example.backend.Service.MatchingInfo.ReceiveBotMoveService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ReceiveBotMoveServiceImpl implements ReceiveBotMoveService{
    @Override
    public String receiveBotMove(Integer userId, Integer direction) {
        System.out.println("receive bot move: " + userId + " " + direction + " ");
        if(WebSocketServer.users.get(userId) != null){
            Game game = WebSocketServer.users.get(userId).game;
            if(game != null){
                if(Objects.equals(game.getPlayerA().getId(), userId)){
                        game.setNextStepA(direction);
                } else if(Objects.equals(game.getPlayerB().getId(), userId)){
                        game.setNextStepB(direction);
                }
            }
        }
        return "receive bot move success";
    }
}
