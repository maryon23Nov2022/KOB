package com.example.backend.Service.Impl.MatchingInfo;

import com.example.backend.Consumer.WebSocketServer;
import com.example.backend.Service.MatchingInfo.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService{
    @Override
    public String startGame(Integer aId, Integer bId) {
        System.out.println("game started: " + aId + " " + bId);
        WebSocketServer.startGame(aId, bId);
        return "start game successfully";
    }
}
