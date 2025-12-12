package com.example.bilabonnement.damageDepartment.controller;

import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * SelectedVehicleModelAdvice
 *
 * Formål:
 *  - Gøre de samme data om den valgte bil tilgængelige i modellen på ALLE sider,
 *    så dine Thymeleaf-fragments (søgebar, statusgrafik, “Information”-boksen)
 *    altid kan rende korrekt – uden at du skal gentage kode i hver controller.
 *
 * Hvordan den virker:
 *  - VehicleSelectionController sætter følgende værdier i HTTP-sessionen:
 *        selectedVehicleId : Integer
 *        statusImage       : String  (fx "4.png")
 *        carStatus         : String  (fx "RENTED")
 *        hasLease          : Boolean
 *  - Denne advice spejler værdierne fra session → model og giver fornuftige defaults.
 *  - Derudover henter den selve Car-objektet for “Information”-boksen.
 */


@ControllerAdvice
public class SelectedVehicleModelAdvice {

    private final CarRepo carRepo;
    private final LeaseContractRepo leaseRepo;

    // constructor
    public SelectedVehicleModelAdvice(CarRepo carRepo, LeaseContractRepo leaseRepo) {
        this.carRepo = carRepo;
        this.leaseRepo = leaseRepo;
    }

    /**
     * Kører før hver controller-metode og lægger standard-attributter i modellen.
     * Bemærk: LÆSER kun fra session – skriver ikke.
     */

    @ModelAttribute
    public void injectSelectedVehicleModel(HttpSession session, Model m) {

//        Når en bruger besøger din app, får de en session — et lille server-side “lager” knyttet til den bruger (via en session-cookie).
//        Du kan gemme nøgleværdier her (fx selectedVehicleId, carStatus) og hente dem igen på senere requests fra samme bruger.

// 1) Læs værdier fra session (sat af VehicleSelectionController)

        Object vId    = session.getAttribute("selectedVehicleId");
        Object sImg   = session.getAttribute("statusImage");
        Object hLease = session.getAttribute("hasLease");
        Object cStat  = session.getAttribute("carStatus");

// 2) Spejl dem 1:1 ind i model, hvis de findes
        if (vId    != null) m.addAttribute("selectedVehicleId", vId);
        if (sImg   != null) m.addAttribute("statusImage",       sImg);
        if (hLease != null) m.addAttribute("hasLease",          hLease);
        if (cStat  != null) m.addAttribute("carStatus",         cStat);


// 3) Sæt sikre defaults så fragments altid kan rende

        if (!m.containsAttribute("statusImage"))       m.addAttribute("statusImage", "0.png");
        if (!m.containsAttribute("selectedVehicleId")) m.addAttribute("selectedVehicleId", null);
        if (!m.containsAttribute("hasLease"))          m.addAttribute("hasLease", false);
        if (!m.containsAttribute("carStatus"))         m.addAttribute("carStatus", null);

// 4) Hent nøgleinformation om bilen til “Information”-boksen (brand, model, osv.)
        //    Kun hvis der faktisk er valgt et vehicleId.
        if (vId instanceof Integer id) {
            Car selectedCar = carRepo.findById(id).orElse(null);
            m.addAttribute("selectedCar", selectedCar);
            // Hvis du en dag vil vise “har lease?” uden at stole på session:
            // boolean exists = leaseRepo.existsByVehicleId(id);
            // m.addAttribute("hasLease", exists);
        } else {
            m.addAttribute("selectedCar", null);
        }
    }
}