package com.example.bilabonnement.damageDepartment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DamageDepartmentController {

    @GetMapping("/damageDepartment")
    public String showDamageDepartment() {
        return "damageDepartmentHTML/damageDepartment";
    }
}