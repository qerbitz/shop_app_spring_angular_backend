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
import javax.validation.Valid;
@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/rejestracja2")
    public String Registration(Model model){

        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }

    @PostMapping("/rejestracja")
    public String processRegistration(@Valid @ModelAttribute("user") User user, Model model, BindingResult bindingResult){
        String username = user.getUsername();
        String password = user.getPassword();
        String e_mail = user.getE_mail();

        User userek = new User();
        userek.setUsername(username);
        userek.setPassword(password);
        userek.setE_mail(e_mail );


        int digit=0;
        int special=0;
        int upCount=0;
        int loCount=0;


        if(userService.checkUniqueness(username)) {
            bindingResult.addError(new FieldError("username", "username", "Użytkownik już istnieje"));
        }
        if(!(password.length()>=8 && password.length()<=20)){
            bindingResult.addError(new FieldError("username", "password", "Hasło musi posiadać od 8 do 20 znaków!"));
            /*for(int i=0; i<password.length(); i++){
                char c = password.charAt(i);
                if(Character.isUpperCase(c)){
                    upCount++;
                }
                if(Character.isLowerCase(c)){
                    loCount++;
                }
                if(Character.isDigit(c)){
                    digit++;
                }
                if(c>=33&&c<=46||c==64){
                    special++;
                }
            }
            if(special>=1&&loCount>=1&&upCount>=1&&digit>=1) {
                userService.saveUser(userek);
                return "goodregistery";
            }
            else{
                return "registration";
            }*/
        }
        if(!isValidEmail(e_mail)){
            bindingResult.addError(new FieldError("username", "e_mail", "Podany e-mail jest niepoprawny"));
        }
        else{
            userService.saveUser(userek);
            return "goodregistery";
        }


        return "registration";
    }

    @GetMapping("/udana_rejestracja")
    public String GoodRegistration(Model theModel){
        return "goodregistery";
    }

    public static boolean isValidEmail(String email) {
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }

}
