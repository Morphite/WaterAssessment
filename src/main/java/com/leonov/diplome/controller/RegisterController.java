package com.leonov.diplome.controller;

import com.leonov.diplome.model.User;
import com.leonov.diplome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String getRegPage() {
        return "register";
    }

    @PostMapping("/register")
    public String regNewUser(User user, Model model) {
        boolean success = userService.register(user, model);

        if (success) {
            return "redirect:/login";
        } else {
            return "register";
        }
    }
}