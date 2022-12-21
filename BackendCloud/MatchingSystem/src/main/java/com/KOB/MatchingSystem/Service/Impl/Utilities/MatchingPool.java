package com.KOB.MatchingSystem.Service.Impl.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{
    private static List<Player> players = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private static RestTemplate restTemplate;
    private static final String startGameURL = "http://127.0.0.1:8090/pk/start/game/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        MatchingPool.restTemplate = restTemplate;
    }
    public void addPlayer(Integer userId, Integer rating, Integer botId){
        lock.lock();
        try{
            players.add(new Player(userId, rating, botId, 0));
        } finally{
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId){
        lock.lock();
        try{
            List<Player> newPlayers = new ArrayList<>();
            for(Player player : players){
                if(!player.getUserId().equals(userId)){
                    newPlayers.add(player);
                }
            }
            players = newPlayers;
        } finally{
            lock.unlock();
        }
    }

    private void increaseTimeWaited(){
        for(Player player: players){
            player.setTimeWaited(player.getTimeWaited() + 1);
        }
    }

    private boolean checkMatched(Player a, Player b){   //Check weather the two players match
        int dif = Math.abs(a.getRating() - b.getRating());
        int minTime = Math.min(a.getTimeWaited(), b.getTimeWaited());
        return dif <= minTime * 10;
    }

    private void sendResult(Player a, Player b){    //return matching result
        System.out.println("send result: " + a + " " + b);
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("a_bot_id", a.getBotId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("b_bot_id", b.getBotId().toString());
        restTemplate.postForObject(startGameURL, data, String.class);
    }

    private void matchPlayers(){
        System.out.println("match players: " + players.toString());
        boolean[] used = new boolean[players.size()];
        for(int i = 0; i < players.size(); ++ i){
            if(used[i]) continue;
            for(int j = i + 1; j < players.size(); ++ j){
                if(used[j]) continue;
                Player a = players.get(i), b = players.get(j);
                if(checkMatched(a, b)){
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for(int i = 0; i < players.size(); ++ i){
            if(!used[i]) newPlayers.add(players.get(i));
        }
        players = newPlayers;
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(1000);
                lock.lock();
                try{
                    increaseTimeWaited();
                    matchPlayers();
                } finally{
                    lock.unlock();
                }
            } catch(InterruptedException e){
                e.printStackTrace();
                break;
            }
        }
    }
}
