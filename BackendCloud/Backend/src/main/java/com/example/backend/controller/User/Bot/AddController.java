package com.example.backend.controller.User.Bot;

import com.example.backend.Service.User.Bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddController {
    @Autowired
    private AddService CurrentAddService;

    @PostMapping("/user/bot/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data){
        return CurrentAddService.Add(data);
    }
}
