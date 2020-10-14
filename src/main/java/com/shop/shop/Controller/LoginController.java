package com.shop.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/showLoginPage")
    public String showLoginPage(){
        return "login/login_form";
    }

    @PostMapping("/showLoginPage")
    public String showLoginPagePost(){
        return "login/login_form";
    }


}
