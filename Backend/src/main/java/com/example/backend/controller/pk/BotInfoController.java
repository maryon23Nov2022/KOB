//Separating front and back ends.

package com.example.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/pk/")
public class BotInfoController{
    @RequestMapping("getbotinfo/")
//http://127.0.0.1:8080/pk/getbotinfo/
    public Map<String, String> getbotinfo(){
        Map<String, String> bot = new HashMap<>();
        bot.put("name", "mary");
        bot.put("rating", "inf");
        return bot;
    }
}
