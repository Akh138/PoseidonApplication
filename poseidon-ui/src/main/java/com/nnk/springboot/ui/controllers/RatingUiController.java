package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.Rating;
import com.nnk.springboot.ui.proxies.RatingProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RatingUiController {

    @Autowired
    private RatingProxy ratingProxy; // Utilisation du Proxy au lieu de RestTemplate

    @GetMapping("/rating/list")
    @CircuitBreaker(name = "ratingService", fallbackMethod = "fallbackRatings")
    public String ratingList(Model model) {
        List<Rating> ratings = ratingProxy.getAllRatings();
        model.addAttribute("ratings", ratings);
        return "rating/list";
    }

    public String fallbackRatings(Model model, Throwable t) {
        model.addAttribute("errorMsg", "⚠️ Le service de Notations (Rating) est indisponible.");
        model.addAttribute("ratings", new ArrayList<Rating>());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addForm(Model model) {
        model.addAttribute("rating", new Rating());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "rating/add";
        }
        ratingProxy.addRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Rating rating = ratingProxy.getRating(id);
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("rating") Rating rating, BindingResult result, Model model) {
        if (result.hasErrors()) {
            rating.setId(id);
            return "rating/update";
        }
        rating.setId(id);
        ratingProxy.updateRating(rating);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ratingProxy.deleteRating(id);
        return "redirect:/rating/list";
    }
}