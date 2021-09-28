package com.shop.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginPage(){
        return "login/login_form";
    }

    @PostMapping("/login")
    public String showLoginPag2e(){
        return "login/login_form";
    }

}
