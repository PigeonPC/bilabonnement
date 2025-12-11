package com.example.bilabonnement.dataRegistration.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataregistrationController {

    // DATAREGISTREING FORSIDE:
    @GetMapping("/dataRegistration")
    public String showDataregistration(Model model) {
        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/dataRegistration";
    }
}