//Separating front and back ends.
package com.example.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class BotInfoController{
    @RequestMapping("/pk/getbotinfo/")      //http://127.0.0.1:8090/pk/getbotinfo/
    public Map<String, String> getbotinfo(){
        Map<String, String> bot = new HashMap<>();
        bot.put("name", "tiger");
        bot.put("rating", "1680");
        return bot;
    }
}