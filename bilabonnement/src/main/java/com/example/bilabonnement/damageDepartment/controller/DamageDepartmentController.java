package com.example.bilabonnement.damageDepartment.controller;

/**
 * DamageDepartmentController
 *
 * Formål:
 *  - Renderer siden /damageDepartment.
 *  - Spejler relevante session-værdier (selectedVehicleId, statusImage, carStatus, hasLease)
 *    ind i modellen med sikre defaults, så Thymeleaf altid kan rendere.
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageDepartmentController {

    @GetMapping("/damageDepartment")
    public String showDamageDepartment(Model model) {
        // Markerer headeren som aktiv
        model.addAttribute("activePage", "damageDepartment");
        return "damageDepartmentHTML/damageDepartment";
    }

    @GetMapping
    public String view() {
        return "damageDepartmentHTML/damageDepartment";
    }

}