package com.example.backend.Service.Impl.User.Bot;

import com.example.backend.Mapper.BotMapper;
import com.example.backend.Service.Impl.Utilities.UserDetailsImpl;
import com.example.backend.Service.User.Bot.RemoveService;
import com.example.backend.pojo.Bot;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {
    @Autowired
    private BotMapper CurrentBotMapper;

    @Override
    public Map<String, String> Remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl UserLoggedIn = (UserDetailsImpl) authenticationToken.getPrincipal();
        User CurrentUser = UserLoggedIn.getUser();

        int BotID = Integer.parseInt(data.get("id"));
        Bot CurrentBot = CurrentBotMapper.selectById(BotID);
        Map<String, String> Result = new HashMap<>();

        if(CurrentBot == null){
            Result.put("error_message", "Bot doesn't exist.");
            return Result;
        }
        if(!CurrentBot.getUserId().equals(CurrentUser.getId())) {
            Result.put("error_message", "Permission denied.");
            return Result;
        }
        CurrentBotMapper.deleteById(BotID);
        Result.put("error_message", "Success");
        return Result;
    }
}
