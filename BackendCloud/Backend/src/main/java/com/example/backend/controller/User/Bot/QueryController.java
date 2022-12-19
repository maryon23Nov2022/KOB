package com.example.backend.controller.User.Bot;

import com.example.backend.Service.User.Bot.QueryService;
import com.example.backend.pojo.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QueryController {
    @Autowired
    private QueryService queryService;

    @GetMapping("/user/bot/query/")
    public List<Bot> Query(){
        return queryService.GetList();
    }
}
