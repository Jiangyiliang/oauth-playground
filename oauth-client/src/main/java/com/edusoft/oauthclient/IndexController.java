package com.edusoft.oauthclient;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping("/test")
    public String test(){
        System.out.println("=======test=========");
        return "/test";
    }

    @GetMapping("/index")
    public String index(){
        System.out.println("=======index=========");
        return "/index";
    }

    @GetMapping("/main")
    public String main(){
        System.out.println("=======main=========");
        return "/main";
    }
}
