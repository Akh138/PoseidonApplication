package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.Trade;
import com.nnk.springboot.ui.proxies.TradeProxy;
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
public class TradeUiController {

    @Autowired
    private TradeProxy tradeProxy;

    @GetMapping("/trade/list")
    @CircuitBreaker(name = "tradeService", fallbackMethod = "fallbackTrades")
    public String tradeList(Model model) {
        List<Trade> trades = tradeProxy.getAllTrades();
        model.addAttribute("trades", trades);
        return "trade/list";
    }

    // Méthode de secours pour Trade
    public String fallbackTrades(Model model, Throwable t) {
        model.addAttribute("errorMsg", "⚠️ Le service des Transactions (Trade) est indisponible.");
        model.addAttribute("trades", new ArrayList<Trade>()); // Liste vide pour éviter l'erreur de boucle
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addForm(Model model) {
        model.addAttribute("trade", new Trade());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "trade/add";
        }
        tradeProxy.addTrade(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeProxy.getTrade(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("trade") Trade trade, BindingResult result, Model model) {
        if (result.hasErrors()) {
            trade.setTradeId(id);
            return "trade/update";
        }
        trade.setTradeId(id);
        tradeProxy.updateTrade(trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        tradeProxy.deleteTrade(id);
        return "redirect:/trade/list";
    }
}