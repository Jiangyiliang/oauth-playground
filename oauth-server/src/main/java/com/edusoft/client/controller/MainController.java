package com.edusoft.client.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/login")
    public String login() {
//        Model model
//        model.addAttribute("loginProcessUrl","/auth/authorize");
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute( "loginError"  , true);
        return "login";
    }

    @RequestMapping("/resForSelf")
    public String resForSelf() {
        return "resForSelf";
    }
}
