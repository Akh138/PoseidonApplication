package com.nnk.springboot.ui.controllers;

import com.nnk.springboot.ui.domain.CurvePoint;
import com.nnk.springboot.ui.proxies.CurvePointProxy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CurvePointUiController {

    @Autowired
    private CurvePointProxy curvePointProxy; // On injecte le Proxy

    @GetMapping("/curvePoint/list")
    @CircuitBreaker(name = "curvepointService", fallbackMethod = "fallbackCurves")
    public String curvePointList(Model model) {
        List<CurvePoint> curvePoints = curvePointProxy.getAllPoints();
        model.addAttribute("curvePoints", curvePoints);
        return "curvePoint/list";
    }

    // Méthode Fallback pour CurvePoint
    public String fallbackCurves(Model model, Throwable t) {
        model.addAttribute("errorMsg", "⚠️ Le service des Courbes (CurvePoint) est indisponible.");
        model.addAttribute("curvePoints", new ArrayList<CurvePoint>());
        // Liste vide pour éviter le plantage
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addForm(Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "curvePoint/add";
        }
        curvePointProxy.addCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint cp = curvePointProxy.getCurvePoint(id);
        model.addAttribute("curvePoint", cp);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("curvePoint") CurvePoint curvePoint, BindingResult result, Model model) {
        if (result.hasErrors()) {
            curvePoint.setId(id);
            return "curvePoint/update";
        }
        curvePoint.setId(id);
        curvePointProxy.updateCurvePoint(curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        curvePointProxy.deleteCurvePoint(id);
        return "redirect:/curvePoint/list";
    }
}