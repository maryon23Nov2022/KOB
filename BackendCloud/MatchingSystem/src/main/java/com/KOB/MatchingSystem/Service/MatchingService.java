package com.KOB.MatchingSystem.Service;

public interface MatchingService {
    String addPlayer(Integer userId, Integer rating);
    String removePlayer(Integer userId);
}
