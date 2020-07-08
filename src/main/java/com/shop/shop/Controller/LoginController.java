package com.shop.shop.Controller;

import com.shop.shop.Entity.User;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String showLoginPage(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "login";}

    @PostMapping("/login")
    public String processlogin(@ModelAttribute("user")User user, Model model){
        String username = user.getUsername();
        String password = user.getPassword();

        if(userService.checkUniqueness(username) && userService.checkUniqueness(password)){
            return "goodregistery";
        }


        model.addAttribute("invalidCredidentials", true);

        return "login";
    }

    @GetMapping("/rejestracja")
    public String Registration(Model theModel){
        return "registration";
    }

    @PostMapping("/rejestracja")
    public String processRegistration(@ModelAttribute("user")User user, Model model){
        String username = user.getUsername();
        String password = user.getPassword();

        return "registration";
    }

    @GetMapping("/udana_rejestracja")
    public String GoodRegistration(Model theModel){
        return "goodregistery";
    }
}
