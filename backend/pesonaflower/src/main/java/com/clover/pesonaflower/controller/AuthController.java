package com.clover.pesonaflower.controller;


import com.clover.pesonaflower.dto.RegistrationDto;
import com.clover.pesonaflower.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);
        return "/register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user")RegistrationDto user){
        userService.saveUser(user);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
