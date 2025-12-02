package com.example.bilabonnement.damageReport.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageReportController {
    @GetMapping("/damage")
    public String damage(Model model) {
        model.addAttribute("activePage", "damage");
        return "damage";
    }
}
