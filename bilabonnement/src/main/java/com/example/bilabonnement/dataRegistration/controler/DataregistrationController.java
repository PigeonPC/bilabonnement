package com.example.bilabonnement.dataRegistration.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DataregistrationController {
    @GetMapping("/dataregistration")
    public String dataregistration(Model model) {
        model.addAttribute("activePage", "dataregistration");
        return "dataregistration";
    }
}
