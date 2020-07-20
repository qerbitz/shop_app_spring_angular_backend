package com.shop.shop.Controller;

import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;


    @GetMapping("/showLoginPage")
    public String showLoginPage(Model model){
        return "login";
    }


}
