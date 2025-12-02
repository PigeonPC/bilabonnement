package com.example.bilabonnement.economy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EconomyController {
    @GetMapping("/economy")
    public String economy(Model model) {
        model.addAttribute("activePage", "economy");
        return "economy";
    }
}