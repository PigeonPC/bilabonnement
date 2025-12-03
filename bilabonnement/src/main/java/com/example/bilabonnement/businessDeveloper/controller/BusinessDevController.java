package com.example.bilabonnement.businessDeveloper.controller;

import com.example.bilabonnement.businessDeveloper.model.BusinessDevDashboard;
import com.example.bilabonnement.businessDeveloper.service.BusinessDevService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusinessDevController {

    @Autowired
    BusinessDevService devService;

    //Test:
    @GetMapping("/test")
    public String testKPI(Model model) {
        int rented = devService.getTotalRentedCars();
        model.addAttribute("rented", rented);
        return "businessDeveloperHTML/businessDeveloper";
    }

    //rigtigt med dashboard:
    @GetMapping("/businessDeveloper")
    public String showDashboard(Model model) {

        //hent data fra service:
        BusinessDevDashboard dashboard = devService.getDashboard();

        //"gør dashboard-objektet tilgængeligt i Thymeleaf som dashboard":
        model.addAttribute("dashboard", dashboard);

        return "businessDeveloperHTML/businessDeveloper";
    }
}

