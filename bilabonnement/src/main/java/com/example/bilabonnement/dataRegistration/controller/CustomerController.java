package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.Customer;
import com.example.bilabonnement.dataRegistration.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //Vis liste over kunder
    @GetMapping("/dataRegistration/customer")
    public String showAllCustomers(Model model) {
        List<Customer> customers = customerService.listAllCustomers();
        model.addAttribute("customers", customers);
        model.addAttribute("title", "Alle kunder");

        model.addAttribute("activePage", "dataRegistration/customer");
        return "dataRegistrationHTML/customer";
    }
}