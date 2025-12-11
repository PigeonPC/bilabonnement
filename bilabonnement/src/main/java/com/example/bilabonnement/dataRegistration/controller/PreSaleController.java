package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.view.*;

import com.example.bilabonnement.dataRegistration.service.PreSaleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PreSaleController {

    private final PreSaleService preSaleService;

    public PreSaleController(PreSaleService preSaleService) {

        this.preSaleService = preSaleService;
    }

    // =============================================== KØBSAFTALER ===============================================
    // VIS TABEL/LISTE OVER KØBSAFTALER:
    @GetMapping("/dataRegistration/preSales")
    public String showPreSaleTable(Model model) {
        List<PreSaleTableView> preSales =
                preSaleService.fetchAllPreSaleAgreementsWithCustomerAndCar();

        model.addAttribute("preSales", preSales);

        return "dataRegistrationHTML/preSales";
    }

    // VIS KØBSAFTALE DETALJESIDE:
    @GetMapping("/dataRegistration/preSales/{id}")
    public String showPreSaleDetail(@PathVariable int id, Model model) {

        PreSaleAgreement preSaleAgreement = preSaleService.getPreSaleAgreement(id);
        Customer customer = preSaleService.getCustomerForPreSale(preSaleAgreement);
        Car car = preSaleService.getCarForPreSale(preSaleAgreement);
        StatusHistory lastStatus = preSaleService.getLatestStatusForPreSale(preSaleAgreement);

        model.addAttribute("preSaleAgreement", preSaleAgreement);
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("lastStatus", lastStatus);

        return "dataRegistrationHTML/preSaleDetail";
    }

}
