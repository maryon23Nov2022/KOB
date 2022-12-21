package com.KOB.MatchingSystem.Service.Impl;

import com.KOB.MatchingSystem.Service.Impl.Utilities.MatchingPool;
import com.KOB.MatchingSystem.Service.MatchingService;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService{

    public static final MatchingPool matchingPool = new MatchingPool();
    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        System.out.println("add player: " + userId + " " + rating);
        matchingPool.addPlayer(userId, rating, botId);
        return "add player successfully";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove player: " + userId);
        matchingPool.removePlayer(userId);
        return "remove player successfully";
    }
}
