package com.example.bilabonnement.controller;

/**
 * VehicleSelectionController
 *
 * Formål:
 *  - Gøre “vælg bil” til en global funktion, der kan bruges fra søgefragmentet på alle sider.
 *  - Ved GET /vehicle/select?vehicleId=...:
 *      * Finder bilen i CarRepo (JPA).
 *      * Afleder statusbilledet ud fra bilens car_status ("1..8.png"; "0.png" som fallback ved ingen bil).
 *      * Sætter følgende i HTTP-sessionen:
 *            selectedVehicleId : Integer
 *            statusImage       : String  ("1.png".."8.png" / "0.png")
 *            carStatus         : String  (ENUM-navnet, fx "RENTED")
 *            hasLease          : Boolean (true hvis der findes en lease for bilen)
 *      * Redirecter tilbage til den side, som udløste søgningen (Referer), ellers /damageDepartment.
 *
 */

import com.example.bilabonnement.dataRegistration.CarStatus;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.Optional;

@Controller
public class VehicleSelectionController {

    private final CarRepo carRepo;
    private final LeaseContractRepo leaseRepo;

    public VehicleSelectionController(CarRepo carRepo, LeaseContractRepo leaseRepo) {
        this.carRepo = carRepo;
        this.leaseRepo = leaseRepo;
    }

/** Hoved-endpoint: kaldt fra søgebaren. Sætter session-state og redirecter tilbage. */

@GetMapping("/vehicle/select")
public String selectByVehicleId(@RequestParam Integer vehicleId,
                                HttpServletRequest request,
                                HttpSession session) {

    carRepo.findById(vehicleId).ifPresentOrElse(car -> {
        session.setAttribute("selectedVehicleId", car.getVehicleId());
        session.setAttribute("statusImage", mapStatusToImage(car.getCarStatus()));
        session.setAttribute("carStatus", car.getCarStatus().name());
        session.setAttribute("hasLease", leaseRepo.existsByVehicleId(car.getVehicleId()));
    }, () -> {
        session.setAttribute("selectedVehicleId", null);
        session.setAttribute("statusImage", "0.png");
        session.setAttribute("carStatus", null);
        session.setAttribute("hasLease", false);
    });

// Simpelt og sikkert redirect:
    String ref = request.getHeader("Referer");
    if (ref != null && ref.startsWith("/") || (ref != null && ref.startsWith(request.getScheme() + "://" + request.getServerName()))) {

    }
    return "redirect:/damageDepartment"; // fast fallback
}



/** Map CarStatus -> filnavn "1..8.png". (0.png håndteres i fallback ovenfor). */

    private String mapStatusToImage(CarStatus s) {
        return switch (s) {
            case PURCHASED -> "1.png";
            case PREPARATION_FOR_RENT -> "2.png";
            case READY_FOR_RENT -> "3.png";
            case RENTED -> "4.png";
            case RETURNED -> "5.png";
            case PREPARATION_FOR_SALE -> "6.png";
            case READY_FOR_SALE -> "7.png";
            case SOLD -> "8.png";
        };
    }

}
