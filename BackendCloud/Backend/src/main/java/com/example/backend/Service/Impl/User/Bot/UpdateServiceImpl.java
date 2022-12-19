package com.example.backend.Service.Impl.User.Bot;

import com.example.backend.Mapper.BotMapper;
import com.example.backend.Service.Impl.Utilities.UserDetailsImpl;
import com.example.backend.Service.User.Bot.UpdateService;
import com.example.backend.pojo.Bot;
import com.example.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper CurrentBotMapper;

    @Override
    public Map<String, String> Update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl UserLoggedIn = (UserDetailsImpl) authenticationToken.getPrincipal();
        User CurrentUser = UserLoggedIn.getUser();

        int BotID = Integer.parseInt(data.get("id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> Result = new HashMap<>();

        if(title == null || title.length() == 0){
            Result.put("error_message", "Please enter a valid bot name.");
            return Result;
        }
        if(title.length() > 64){
            Result.put("error_message", "Too long for title.");
            return Result;
        }
        if(description != null && description.length() > 4096){
            Result.put("error_message", "Too long for description.");
            return Result;
        }
        if(content == null || content.length() == 0) {
            Result.put("error_message", "Please submit a valid code.");
            return Result;
        }
        if(content.length() > 4096){
            Result.put("error_message", "Too long for code.");
            return Result;
        }
        Bot CurrentBot = CurrentBotMapper.selectById(BotID);
        if(CurrentBot == null){
            Result.put("error_message", "Bot doesn't exist.");
            return Result;
        }
        if(!CurrentBot.getUserId().equals(CurrentUser.getId())) {
            Result.put("error_message", "Permission denied.");
            return Result;
        }
        Bot NewBot = new Bot(
            CurrentBot.getId(),
            CurrentUser.getId(),
            title,
            description,
            content
        );
        CurrentBotMapper.updateById(NewBot);
        Result.put("error_message", "Success");
        return Result;
    }
}
