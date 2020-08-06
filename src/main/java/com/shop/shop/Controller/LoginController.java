package com.shop.shop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/showLoginPage")
    public String showLoginPage(Model model){
        return "login";
    }


}
