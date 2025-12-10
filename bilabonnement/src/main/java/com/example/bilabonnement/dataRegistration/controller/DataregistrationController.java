package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.view.CarView;
import com.example.bilabonnement.dataRegistration.model.view.*;
import com.example.bilabonnement.dataRegistration.service.BookingService;
import com.example.bilabonnement.dataRegistration.service.CarService;
import com.example.bilabonnement.dataRegistration.service.LeaseContractService;

import com.example.bilabonnement.dataRegistration.service.PreSaleService;
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
    private final PreSaleService preSaleService;
    private final BookingService bookingService;

    public DataregistrationController(LeaseContractService leaseContractService,
                                      CarService carService,
                                      PreSaleService preSaleService,
                                      BookingService bookingService) {
        this.leaseContractService = leaseContractService;
        this.carService = carService;
        this.preSaleService = preSaleService;
        this.bookingService = bookingService;
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
                bookingService.fetchAllBookingsWithRenterNameAndCarModel();

        model.addAttribute("bookings", bookings);

        return "dataRegistrationHTML/bookings";
    }

    //Vis specifik booking
    @GetMapping("/dataRegistration/bookings/{id}")
    public String showBookingDetail(@PathVariable("id") int leasingContractId, Model model) {

        LeaseContract leaseContract = bookingService.getLeaseContract(leasingContractId);
        Renter renter = bookingService.getRenterForLease(leaseContract);
        Customer customer = bookingService.getCustomerForRenter(renter);
        Car car = bookingService.getCarForLease(leaseContract);
        StatusHistory lastStatus = bookingService.getLatestStatusForLease(leaseContract);

        model.addAttribute("leaseContract", leaseContract);
        model.addAttribute("renter", renter);
        model.addAttribute("customer", customer);
        model.addAttribute("car", car);
        model.addAttribute("lastStatus", lastStatus);

        return "dataRegistrationHTML/bookingDetail";
    }

    //Godkend booking
    @PostMapping("/dataRegistration/bookings/{id}/approve")
    public String approveBooking(@PathVariable int id, RedirectAttributes redirectAttributes) {

        boolean success = bookingService.approveLeaseContractByIdAndUpdateCarStatus(id);

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



    //Vis liste over pre Sale agreements med Navn og bil
    @GetMapping("/dataRegistration/preSales")
    public String showPreSaleTable(Model model) {
        List<PreSaleTableView> preSales =
                preSaleService.fetchAllPreSaleAgreementsWithCustomerAndCar();

        model.addAttribute("preSales", preSales);

        return "dataRegistrationHTML/preSales";
    }

    // Vis specifik presale agreement - kun med rene entiteter ikke DTO
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

//        //Der findes biler uden lejekontrakter, derfor try/catch...
//        //Tjek om der er en LeaseContract tilknyttet bil id'et:
//        Integer leaseContractId= null;
//
//        try{
//            LeaseContract leaseContract = leaseContractService.findContractByVehicleID(vehicleId);
//            if (leaseContract != null){
//                leaseContractId = leaseContract.getLeasingContractId();
//            }
//        } catch (Exception e){
//
//            //hvis der ikke er nogen kontrakt på bilen, skal der bare ikke stå noget.
//        }
//        //Tilføj/send leaseContract til view:
//        model.addAttribute("leaseContractId", leaseContractId);


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

    //Se siden hvor man kan ændre status på en bil:
    @GetMapping("/dataRegistration/cars/{id}/changeStatus")
    public String showChangeStatusForm(@PathVariable("id") int vehicleId, Model model){

        //Hent bilen og vis nuværende info:
        CarView car = carService.findCarById(vehicleId);

        model.addAttribute("car", car);
        model.addAttribute("title", "Skift status for bil med id nr. " + vehicleId);

        return "dataRegistrationHTML/carChangeStatus";
    }

    //Ændre status for en bil:
    @PostMapping("/dataRegistration/cars/{id}/status")
    public String changeCarStatus(@PathVariable("id") int vehicleId, @RequestParam String status){


        carService.changeCarStatus(vehicleId, status);

        return "redirect:/dataRegistration/cars/" + vehicleId;
    }

}
