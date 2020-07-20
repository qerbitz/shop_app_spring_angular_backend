package com.shop.shop.Controller;

import com.shop.shop.Entity.User;
import com.shop.shop.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/showLoginPage")
    public String showLoginPage(Model model){
        return "login";
    }


    @GetMapping("/login")
    public void test(HttpServletResponse response) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().toString();

        if(role.contains("1"))
        response.sendRedirect("/product");

    }

    @PostMapping("/login")
    public String processlogin(@ModelAttribute("user")User user, Model model){
        String username = user.getUsername();
        String password = user.getPassword();

        //StringBuilder password2 = new StringBuilder("{bcrypt}").append(passwordEncoder.encode(user.getPassword()));

        if(userService.checkUniqueness(username) && userService.checkUniqueness(password)){
            return "products";
        }


        model.addAttribute("invalidCredidentials", true);

        return "login";
    }

}
