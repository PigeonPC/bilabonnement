package com.example.bilabonnement.damageDepartment.controller;

import com.example.bilabonnement.dataRegistration.CarStatus;
import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import com.example.bilabonnement.dataRegistration.repository.StatusHistoryRepo;
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
    private final StatusHistoryRepo statusRepo;

    public VehicleSelectionController(CarRepo carRepo,
                                      LeaseContractRepo leaseRepo,
                                      StatusHistoryRepo statusRepo) {
        this.carRepo = carRepo;
        this.leaseRepo = leaseRepo;
        this.statusRepo = statusRepo;
    }

    /** Kaldt fra søgebaren: sæt session-værdier og redirect tilbage til samme side. */
    @GetMapping("/vehicle/select")
    public String selectByVehicleId(@RequestParam Integer vehicleId,
                                    HttpServletRequest request,
                                    HttpSession session) {

        Optional<Car> carOpt = carRepo.findById(vehicleId);

        if (carOpt.isPresent()) {
            Car car = carOpt.get();

            // 1) Gem det valgte vehicleId i session
            session.setAttribute("selectedVehicleId", car.getVehicleId());

            // 2) Find NYESTE status fra status_histories (ikke fra cars)
            CarStatus latest = statusRepo.findLatestStatusForVehicle(car.getVehicleId()).orElse(null);

            // 3) Map status -> billede (fallback til 0.png hvis ingen status)
            String image = (latest != null) ? mapStatusToImage(latest) : "0.png";
            session.setAttribute("statusImage", image);
            session.setAttribute("carStatus", latest != null ? latest.name() : null);

            // 4) Har bilen en (mindst én) lease?
            session.setAttribute("hasLease", leaseRepo.existsByVehicleId(car.getVehicleId()));

        } else {
            // Ukendt vehicleId → nulstil
            session.setAttribute("selectedVehicleId", null);
            session.setAttribute("statusImage", "0.png");
            session.setAttribute("carStatus", null);
            session.setAttribute("hasLease", false);
        }

        // Tilbage til den side søgningen kom fra (eller /damageDepartment som fallback)
        String back = safeRefererPath(request.getHeader("Referer"));
        return "redirect:" + (back != null ? back : "/damageDepartment");
    }

    /** Status -> filnavn til grafik. */
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

    /** Træk sikkert path(+query) ud af Referer til intern redirect. */
    private String safeRefererPath(String ref) {
        if (ref == null) return null;
        try {
            URI uri = new URI(ref);
            String path = uri.getPath();
            String query = uri.getQuery();
            return (query == null) ? path : path + "?" + query;
        } catch (Exception e) {
            return null;
        }
    }
}
