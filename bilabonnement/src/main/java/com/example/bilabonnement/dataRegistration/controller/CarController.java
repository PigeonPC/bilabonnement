package com.example.bilabonnement.dataRegistration.controller;

import com.example.bilabonnement.dataRegistration.model.view.CarView;
import com.example.bilabonnement.dataRegistration.service.CarService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class CarController {

    private final CarService carService;

    public CarController(CarService carService)
    {
        this.carService = carService;
    }


    // =============================================== BILER ===============================================
    // VIS LISTE OVER BILER FRA CARS TABELLEN:
    @GetMapping("/dataRegistration/cars")
    public String showAllCars(Model model) {
        List<CarView> cars = carService.listAllCars();
        model.addAttribute("cars", cars);
        model.addAttribute("title", "Alle biler");

        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/cars";
    }

    // VIS DETALJER FOR EN SPECIFIK BIL UD FRA ID:
    @GetMapping("/dataRegistration/cars/{id}")
    public String showCarDetails(@PathVariable("id") int vehicleId, Model model){
        CarView car = carService.findCarById(vehicleId);

        model.addAttribute("car", car);
        model.addAttribute("title", "Detaljer for bil " + vehicleId);
        model.addAttribute("activePage", "dataRegistration");

        //Henter lejekontrakten på bilen, hvis den har en:
//        LeaseContract leaseContract = leaseContractService.findContractByVehicleIDOrNull(vehicleId);
//        if (leaseContract != null){
//            model.addAttribute("leaseContractId", leaseContract.getLeasingContractId());
//        }

        return "dataRegistrationHTML/carDetail";
    }

    // SE ALLE BILER DER ER KLAR TIL UDLEJNING:
    @GetMapping("/dataRegistration/cars/ready")
    public String showCarsReadyForRent(Model model){
        List<CarView> cars = carService.listAllCarsByStatus("READY_FOR_RENT");

        model.addAttribute("cars", cars);
        model.addAttribute("title", "Biler klar til udlejning");
        model.addAttribute("activePage", "dataRegistration");
        return "dataRegistrationHTML/cars";
    }

    // SE ALLE BILER UD FRA STATUS, VÆLG STATUS:
    @GetMapping("/dataRegistration/carStatus")
    public String showCarStatusForm(Model model){

        model.addAttribute("cars", Collections.emptyList());
        model.addAttribute("status", null);
        model.addAttribute("title", "Find bil ud fra status");
        return "dataRegistrationHTML/carsByStatus";


    }

    // SE ALLE BILER UD FRA DEN VALGTE STATUS:
    @PostMapping("/dataRegistration/carStatus")
    public String showCarsStatusSearch(@RequestParam String status, Model model){
        List<CarView> cars = carService.listAllCarsByStatus(status);

        model.addAttribute("cars", cars);
        model.addAttribute("status", status);
        model.addAttribute("title", "Find bil ud fra status");

        return "dataRegistrationHTML/carsByStatus";

    }

    // SE SIDEN HVOR MAN KAN ÆNDRE STATUS PÅ EN BIL:
    @GetMapping("/dataRegistration/cars/{id}/changeStatus")
    public String showChangeStatusForm(@PathVariable("id") int vehicleId, Model model){

        //Hent bilen og vis nuværende info:
        CarView car = carService.findCarById(vehicleId);

        model.addAttribute("car", car);
        model.addAttribute("title", "Skift status for bil med id nr. " + vehicleId);

        return "dataRegistrationHTML/carChangeStatus";
    }

    // ÆNDRE STATUS FOR EN BIL:
    @PostMapping("/dataRegistration/cars/{id}/status")
    public String changeCarStatus(@PathVariable("id") int vehicleId, @RequestParam String status){


        carService.changeCarStatus(vehicleId, status);

        return "redirect:/dataRegistration/cars/" + vehicleId;
    }

}
