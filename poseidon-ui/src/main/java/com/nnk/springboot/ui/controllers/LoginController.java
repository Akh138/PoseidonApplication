package com.nnk.springboot.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login"; // Affiche login.html
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}