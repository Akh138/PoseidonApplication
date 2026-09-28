package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.User;
import com.nnk.springboot.ui.proxies.UserProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserUiController {

    @Autowired
    private UserProxy userProxy; // On injecte le nouveau Proxy

    @GetMapping("/users/list")
    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUsers")
    public String userList(Model model) {
        List<User> users = userProxy.getAllUsers();
        model.addAttribute("users", users);
        return "user/list";
    }

    // Méthode de secours pour la liste des utilisateurs
    public String fallbackUsers(Model model, Throwable t) {
        model.addAttribute("errorMsg", "⚠️ Le service d'administration des utilisateurs est indisponible.");
        model.addAttribute("users", new ArrayList<User>()); // Liste vide
        return "user/list";
    }

    @GetMapping("/users/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/users/validate")
    public String validate(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/add";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userProxy.addUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userProxy.getUser(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/users/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "user/update";
        }
        user.setId(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userProxy.updateUser(user);
        return "redirect:/users/list";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userProxy.deleteUser(id);
        return "redirect:/users/list";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        User user = new User();
        user.setRole("USER"); // Rôle pré-rempli
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/validate")
    public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }
        user.setRole("USER");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));

        userProxy.addUser(user);
        return "redirect:/login?success";
    }
}