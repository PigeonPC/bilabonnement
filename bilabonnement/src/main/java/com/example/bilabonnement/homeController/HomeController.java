package com.example.bilabonnement.homeController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    // Login/index side
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("activePage", "login");
        return "/login";
    }

    // Vis mere side (TEST SIDE)
    @GetMapping("/visMere")
    public String visMere(Model model) {
        model.addAttribute("activePage", "visMere");
        return "visMereSkabelon";
    }
}
