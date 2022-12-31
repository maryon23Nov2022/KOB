package com.example.backend.controller.User.Bot;

import com.example.backend.Service.User.Bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveController {
    @Autowired
    private RemoveService CurrentRemoveService;

    @PostMapping("/api/user/bot/remove/")
    public Map<String, String> Remove(@RequestParam Map<String, String> data){
        return CurrentRemoveService.Remove(data);
    }
}
