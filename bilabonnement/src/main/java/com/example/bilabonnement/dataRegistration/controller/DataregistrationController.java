package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.*;

import com.example.bilabonnement.dataRegistration.service.CarService;
import com.example.bilabonnement.dataRegistration.service.LeaseContractService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

@Controller
public class DataregistrationController {

    private final LeaseContractService leaseContractService;

    private final CarService carService;

    public DataregistrationController(LeaseContractService leaseContractService, CarService carService) {
        this.leaseContractService = leaseContractService;
        this.carService = carService;
    }

    @GetMapping("/visMere")
    public String visMere(Model model) {
        model.addAttribute("activePage", "visMere");
        return "visMereSkabelon";
    }

    // Forsiden til dataregistrering
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

    //Vis specifik booking
    @GetMapping("/dataRegistration/bookings/{id}")
    public String showBookingDetail(@PathVariable("id") int leasingContractId, Model model) {

        BookingDetailView booking = leaseContractService.fetchBookingDetailByIdPlusCustomerRenterAndCar(leasingContractId);
        model.addAttribute("booking", booking);

        return "dataRegistrationHTML/bookingDetail";
    }

    //Godkender en booking
    @PostMapping("/dataRegistration/bookings/{id}/approve")
    public String approveBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {

        boolean success = leaseContractService.approveLeaseContractByIdAndUpdateCarStatus(id);

        if (success) {
            redirectAttributes.addFlashAttribute("message", "Booking godkendt!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Kunne ikke godkende booking.");
        }

        return "redirect:/dataRegistration/bookings";
    }

    //Vis liste over godkendte lejekontrakter med Navn og bil
    @GetMapping("/dataRegistration/leaseContracts")
    public String showLeaseContractTable(Model model) {
        List<LeaseContractTableView> leaseContracts =
                leaseContractService.fetchAllLeaseContractsWithRenterNameAndCarModel();

        model.addAttribute("leaseContracts", leaseContracts);

        return "dataRegistrationHTML/leaseContracts";
    }


    //Vis specifik lejekontrakt
    @GetMapping("/dataRegistration/leaseContracts/{id}")
    public String showLeaseContractDetail(@PathVariable int id, Model model) {
        LeaseContractDetailView leaseContract = leaseContractService.fetchLeaseContractDetailByIdPlusCustomerRenterAndCar(id);
        model.addAttribute("leaseContract", leaseContract);

        return "dataRegistrationHTML/leaseContractDetail";
    }


    //Vis liste over biler fra cars tabellen:
    @GetMapping("/dataRegistration/cars")
    public String showAllCars(Model model) {
        List<CarView> cars = carService.listAllCars();
        model.addAttribute("cars", cars);
        model.addAttribute("title", "Alle biler");

        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/cars";
    }

    //Vis detaljer for en specifik bil ud fra id:
    @GetMapping("/dataRegistration/cars/{id}")
    public String showCarDetails(@PathVariable("id") int vehicleId, Model model){
        CarView car = carService.findCarById(vehicleId);

        model.addAttribute("car", car);
        model.addAttribute("title", "Detaljer for bil " + vehicleId);
        model.addAttribute("activePage", "dataRegistration");

        return "dataRegistrationHTML/carDetail";
    }


    //Se alle biler der er klar til udlejning:
    @GetMapping("/dataRegistration/cars/ready")
    public String showCarsReadyForRent(Model model){
        List<CarView> cars = carService.listAllCarsByStatus("READY_FOR_RENT");

        model.addAttribute("cars", cars);
        model.addAttribute("title", "Biler klar til udlejning");
        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/cars";
    }

    //Se alle biler ud fra status, vælg status:
    @GetMapping("/dataRegistration/carStatus")
    public String showCarStatusForm(Model model){

        model.addAttribute("cars", Collections.emptyList());
        model.addAttribute("status", null);
        model.addAttribute("title", "Find bil ud fra status");
        return "dataRegistrationHTML/carsByStatus";


    }


    //Se alle biler ud fra valgte status:
    @PostMapping("/dataRegistration/carStatus")
    public String showCarsStatusSearch(@RequestParam String status, Model model){
        List<CarView> cars = carService.listAllCarsByStatus(status);

        model.addAttribute("cars", cars);
        model.addAttribute("status", status);
        model.addAttribute("title", "Find bil ud fra status");

        return "dataRegistrationHTML/carsByStatus";

    }



}
