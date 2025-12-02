package com.example.bilabonnement.businessdevelopers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessDevController {
    @GetMapping("/businessdevelopers")
    public String businessdevelopers(Model model) {
        model.addAttribute("activePage", "businessdevelopers");
        return "businessdevelopers";
    }
}

