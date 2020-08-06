package com.shop.shop.Controller;

import com.shop.shop.Entity.User;
import com.shop.shop.Service.Interface.UserService;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/processRegistration")
    public String processRegistration(@Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult){

        if(userService.checkUniqueness(user.getUsername())) {
            bindingResult.addError(new FieldError("username", "username", "Użytkownik już istnieje"));
        }
        if(!(user.getPassword().length()>=8 && user.getPassword().length()<=20)){
            bindingResult.addError(new FieldError("username", "password", "Hasło musi posiadać od 8 do 20 znaków!"));
        }
        if(!isValidEmail(user.getE_mail())){
            bindingResult.addError(new FieldError("username", "e_mail", "Podany e-mail jest niepoprawny"));
        }
        else{
            userService.saveUser(user);
            return "goodregistery";
        }


        return "registration";
    }

    @GetMapping("/good_registery")
    public String GoodRegistration(Model theModel){
        return "goodregistery";
    }

    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }

}
