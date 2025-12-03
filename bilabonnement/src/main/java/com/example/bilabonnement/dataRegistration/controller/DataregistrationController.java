package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.BookingTableView;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.service.LeaseContractService;
import com.example.bilabonnement.forms.damageReportForm.model.DamageItem;
import com.example.bilabonnement.forms.damageReportForm.model.DamageReport;
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


    //Forsiden til dataregistrering
    @GetMapping("/dataregistration")
    public String showDataregistration(Model model) {
        model.addAttribute("activePage", "dataregistration");
        return "dataRegistrationHTML/dataRegistration";
    }

    //Vis liste over bookinger
    @GetMapping("/dataregistration/bookings")
    public String showBookingsTable(Model model) {
        List<BookingTableView> bookings =
                leaseContractService.fetchAllBookingsWithRenterNameAndCarModel();

        model.addAttribute("bookings", bookings);

        return "dataRegistrationHTML/bookings";
    }

}
