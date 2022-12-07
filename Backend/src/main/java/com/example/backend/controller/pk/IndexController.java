//No separation between front and back end.

package com.example.backend.controller.pk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
    @RequestMapping("/")        //http://127.0.0.1:8090/
    public String index(){
        return "pk/index.html";     //src/main/resources/templates/pk/index.html
    }
}
