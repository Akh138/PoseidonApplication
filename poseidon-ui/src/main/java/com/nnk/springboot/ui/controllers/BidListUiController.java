package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.BidList;
import com.nnk.springboot.ui.proxies.BidListProxy; // On importe le nouveau smartphone
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BidListUiController {

    // On remplace RestTemplate par notre Proxy intelligent
    @Autowired
    private BidListProxy bidListProxy;

    @GetMapping("/bidList/list")
    @CircuitBreaker(name = "bidlistService", fallbackMethod = "fallbackBids")
    public String home(Model model) {
        // Avant : RestTemplate appelait l'URL brute
        // Maintenant : On appelle simplement la méthode du Proxy
        List<BidList> bids = bidListProxy.getAllBids();

        model.addAttribute("bidLists", bids);
        return "bidList/list";
    }

    // C'EST LA MÉTHODE DE SECOURS (FALLBACK)
    // Elle doit avoir la même signature + un paramètre Throwable
    public String fallbackBids(Model model, Throwable t) {
        // On envoie un message d'erreur à la page
        model.addAttribute("errorMsg", "⚠️ Le service d'offres (BidList) est actuellement en maintenance. Veuillez réessayer plus tard.");
        // On envoie quand même une liste vide pour éviter que le tableau ne fasse une erreur Java
        model.addAttribute("bidLists", new ArrayList<BidList>());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidList());
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid @ModelAttribute("bidList") BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "bidList/add";
        }

        // Appel simplifié au microservice pour l'ajout
        bidListProxy.addBidList(bidList);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id) {
        // Appel simplifié pour la suppression
        bidListProxy.deleteBidList(id);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // Récupération d'un objet précis via le Proxy
        BidList bidList = bidListProxy.getBidList(id);

        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid @ModelAttribute("bidList") BidList bidList, BindingResult result, Model model) {
        if (result.hasErrors()) {
            bidList.setBidListId(id);
            return "bidList/update";
        }

        bidList.setBidListId(id);
        // Envoi de la mise à jour via le Proxy
        bidListProxy.updateBidList(bidList);
        return "redirect:/bidList/list";
    }
}