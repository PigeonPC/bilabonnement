package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.model.view.*;
import com.example.bilabonnement.dataRegistration.service.LeaseContractService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class LeaseContractController {

    private final LeaseContractService leaseContractService;

    public LeaseContractController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    // =============================================== LEJEKONTRAKT  ===============================================

    // VIS TABEL/LISTE OVER LEJEKONTRAKTER:
    @GetMapping("/dataRegistration/leaseContracts")
    public String showLeaseContractTable(Model model) {
        List<LeaseContractTableView> leaseContracts =
                leaseContractService.fetchAllLeaseContractsWithRenterNameAndCarModel();

        model.addAttribute("leaseContracts", leaseContracts);

        return "dataRegistrationHTML/leaseContracts";
    }


    // VIS LEJEKONTRAKT DETALJESIDE:
    @GetMapping("/dataRegistration/leaseContracts/{id}")
    public String showLeaseContractDetail(@PathVariable("id") int leasingContractId, Model model) {

        LeaseContract leaseContract = leaseContractService.getLeaseContract(leasingContractId);
        Renter renter = leaseContractService.getRenterForLease(leaseContract);
        Customer customer = leaseContractService.getCustomerForRenter(renter);
        Car car = leaseContractService.getCarForLease(leaseContract);
        StatusHistory lastStatus = leaseContractService.getLatestStatusForLease(leaseContract);

        model.addAttribute("leaseContract", leaseContract);
        model.addAttribute("renter", renter);
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("lastStatus", lastStatus);

        return "dataRegistrationHTML/leaseContractDetail";
    }
}