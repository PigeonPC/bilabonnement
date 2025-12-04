package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.BookingDetailView;
import com.example.bilabonnement.dataRegistration.model.BookingTableView;

import com.example.bilabonnement.dataRegistration.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DataregistrationController {
    private final LeaseContractService leaseContractService;

    public DataregistrationController(LeaseContractService leaseContractService) {
        this.leaseContractService = leaseContractService;
    }

    @GetMapping("/visMere")
    public String visMere(Model model) {
        model.addAttribute("activePage", "visMere");
        return "visMereSkabelon";
    }


    //Forsiden til dataregistrering
    @GetMapping("/dataRegistration")
    public String showDataregistration(Model model) {
        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/dataRegistration";
    }

    //Vis liste over bookinger
    @GetMapping("/dataRegistration/bookings")
    public String showBookingsTable(Model model) {
        List<BookingTableView> bookings =
                leaseContractService.fetchAllBookingsWithRenterNameAndCarModel();

        model.addAttribute("bookings", bookings);

        return "dataRegistrationHTML/bookings";
    }

    //Vis specifik booking, tager imod de id fra den booking man har trykket på
    @GetMapping("/dataRegistration/bookings/{id}")
    public String showBookingDetail(@PathVariable("id") int leasingContractId, Model model) {

        BookingDetailView booking = leaseContractService.fetchBookingDetailByIdPlusCustomerAndCar(leasingContractId);
        model.addAttribute("booking", booking);

        return "dataRegistrationHTML/bookingDetail";
    }
}