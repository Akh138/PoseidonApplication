package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.RuleName;
import com.nnk.springboot.ui.proxies.RuleNameProxy;
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
public class RuleNameUiController {

    @Autowired
    private RuleNameProxy ruleNameProxy;

    @GetMapping("/ruleName/list")
    @CircuitBreaker(name = "rulenameService", fallbackMethod = "fallbackRules")
    public String ruleNameList(Model model) {
        List<RuleName> ruleNames = ruleNameProxy.getAllRules();
        model.addAttribute("ruleNames", ruleNames);
        return "ruleName/list";
    }

    // Méthode de secours pour RuleName
    public String fallbackRules(Model model, Throwable t) {
        model.addAttribute("errorMsg", "⚠️ Le service des Règles (RuleName) est indisponible.");
        model.addAttribute("ruleNames", new ArrayList<RuleName>());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addForm(Model model) {
        model.addAttribute("ruleName", new RuleName());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ruleName/add";
        }
        ruleNameProxy.addRule(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleName rule = ruleNameProxy.getRule(id);
        model.addAttribute("ruleName", rule);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("ruleName") RuleName ruleName, BindingResult result, Model model) {
        if (result.hasErrors()) {
            ruleName.setId(id);
            return "ruleName/update";
        }
        ruleName.setId(id);
        ruleNameProxy.updateRule(ruleName);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        ruleNameProxy.deleteRule(id);
        return "redirect:/ruleName/list";
    }
}