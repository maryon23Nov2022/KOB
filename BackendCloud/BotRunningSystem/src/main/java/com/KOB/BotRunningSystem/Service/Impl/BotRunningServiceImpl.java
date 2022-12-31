package com.KOB.BotRunningSystem.Service.Impl;

import com.KOB.BotRunningSystem.Service.BotRunningService;
import com.KOB.BotRunningSystem.Service.Impl.Utilies.BotPool;
import org.springframework.stereotype.Service;

@Service
public class BotRunningServiceImpl implements BotRunningService{
    public static final BotPool botPool = new BotPool();

    @Override
    public String addBot(Integer userId, String botCode, String input){
//        System.out.println("add bot: " + userId + " " + botCode + " " + input);
        botPool.addBot(userId, botCode, input);
        return "bot added successfully";
    }
}
