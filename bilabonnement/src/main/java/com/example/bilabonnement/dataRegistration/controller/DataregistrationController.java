package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.BookingTableView;
import com.example.bilabonnement.dataRegistration.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataregistrationController {

    private final LeaseContractService leaseContractService;

    public DataregistrationController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    // Forsiden til dataregistrering
    @GetMapping("/dataRegistration")
    public String showDataregistration(Model model) {
        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/dataRegistration";
    }

    // Vis liste over bookinger
    @GetMapping("/dataregistration/bookings")
    public String showBookingsTable(Model model) {
        List<BookingTableView> bookings =
                leaseContractService.fetchAllBookingsWithRenterNameAndCarModel();
        model.addAttribute("bookings", bookings);
        return "dataRegistrationHTML/bookings";
    }

    @GetMapping("/visMere")
    public String visMere(Model model) {
        model.addAttribute("activePage", "visMere");
        return "visMereSkabelon";
    }
}
