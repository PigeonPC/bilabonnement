package com.example.bilabonnement.damage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageController {
    @GetMapping("/damage")
    public String damage(Model model) {
        model.addAttribute("activePage", "damage");
        return "damage";
    }
}
