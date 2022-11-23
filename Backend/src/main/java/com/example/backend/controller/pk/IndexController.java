//No separation between front and back end.

package com.example.backend.controller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pk")
public class IndexController {
    @RequestMapping("index/")
//http://127.0.0.1:8080/pk/index/
    public String inded(){
        return "pk/index.html";
    }
}
