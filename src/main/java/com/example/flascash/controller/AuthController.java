package com.example.flascash.controller;

import com.example.flascash.entities.User;
import com.example.flascash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        System.out.println("Rendering signup page...");
        return "signup";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            System.out.println("Error during registration: " + bindingResult.getAllErrors());
            model.addAttribute("user", user);
            return "signup";
        }

        try {
            userService.registerUser(user.getEmail(), user.getUsername(), user.getPassword());
            return "redirect:/signin";
        } catch (IllegalArgumentException e) {
            System.out.println("Error during registration: " + e.getMessage());
            model.addAttribute("error", e.getMessage());
            model.addAttribute("user", user);
            return "signup";
        }
    }

    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }
}
