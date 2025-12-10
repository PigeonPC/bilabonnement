package com.example.bilabonnement.damageDepartment.forms.damageReportForm.controller;

/**
 * DamageReportController
 *
 * Formål:
 *  - Vise/oprette/gemme tilstandsrapporter (damage reports) for en bil.
 *  - Routing baseres på vehicleId i URL'en; controlleren finder selv leaseId robust.
 *
 * Routes:
 *  - GET  /damageReportForm/view?vehicleId=...  → vis eksisterende rapport (opretter aldrig)
 *  - GET  /damageReportForm/new?vehicleId=...   → opret ny rapport, kun hvis ingen findes
 *  - POST /damageReportForm/save                → gem rapport og redirect til /view?vehicleId=...
 *
 * View:
 *  - Returnerer "damageDepartmentHTML/damageReportForm"
 *  - Lægger altid "damageReport" i modellen (og flade felter: vehicleId, leaseId, renterId, ...)
 */

import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageItem;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageReport;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.service.DamageReportService;
import com.example.bilabonnement.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/damageReportForm")
public class DamageReportController {

    @Autowired private DamageReportService damageReportService;
    @Autowired private LeaseContractRepo leaseContractRepo;
    @Autowired private CarRepo carRepo;


    /* -------------------------- VIEW (kun læse) -------------------------- */

    /** Viser KUN eksisterende rapport. Opretter aldrig i /view. */
    @GetMapping("/view")
    public String viewDamageReport(@RequestParam(name="vehicleId", required=false) String vehicleIdStr,
                                   Model model) {

        // Parse request-param til Integer på en sikker måde (null ved tom/ugyldig)
        Integer vehicleId = parseIntOrNull(vehicleIdStr);

        // 1) Find leaseId robust ud fra vehicleId (Optional -> null hvis ingen fundet).
        //    Vi bruger en helper i repo'et, så vi undgår exceptions ved 0 rækker.
        Integer leaseId = (vehicleId == null)
                ? null
                : leaseContractRepo.findLeaseIdByVehicleId(vehicleId).orElse(null);

        // 2) Forbered et DamageReport-objekt UANSET hvad, så Thymeleaf altid har noget at binde til.
        DamageReport report;

        if (leaseId == null) {
            // Ingen lease til denne bil → vis stabil, tom rapport
            // (så formularen kan renderes, men med “Ingen data”-signaler)
            report = new DamageReport();
            report.setLateReturn(false);           // default
            report.setPaidStatus(false);           // default
            report.setDamageItems(new ArrayList<>());
            ensureOneBlankRow(report);             // giv bruger et tomt felt at skrive i

            // Læg simple flags/info i modellen til visning i view’et
            model.addAttribute("vehicleId", vehicleId);
            model.addAttribute("noLeaseForVehicle", vehicleId != null); // “bilen findes, men ingen lease”
            model.addAttribute("noReport", true);                       // “ingen rapport at vise”

            model.addAttribute("damageReport", report);
            return "damageDepartmentHTML/damageReportForm";
        }

        // 3) Vi har en lease → forsøg at hente eksisterende rapport inkl. items
        report = damageReportService.findByLeaseIdWithItems(leaseId);

        if (report == null) {
            // Ingen rapport endnu → vi binder i det mindste leasingContractId,
            // så feltet vises udfyldt i formularen
            report = new DamageReport();
            report.setLeasingContractId(leaseId);
            report.setLateReturn(false);           // default
            report.setPaidStatus(false);           // default
            report.setDamageItems(new ArrayList<>());
            ensureOneBlankRow(report);
            model.addAttribute("noReport", true);
        } else {
            // Sikr at der er mindst ét item til formular-binding
            if (report.getDamageItems() == null) {
                report.setDamageItems(new ArrayList<>());
            }
            ensureOneBlankRow(report);

            // Synkronisér paidStatus ud fra hasPayed-dato og beregn totaler
            report.updatePaidStatusFromDate();
            damageReportService.recalc(report);
        }

        // 4) Når vi har leaseId, lægger vi altid lease-/bil-info i modellen
        //    (vehicleId, renterId, drivenKm, extraKmPrice, mv.)
        loadLeaseInfo(leaseId, model, report.getTotalKm());

        // Brugervenlighed: behold også vehicleId og leaseId som flade felter i modellen
        model.addAttribute("vehicleId", vehicleId);
        model.addAttribute("leaseId", leaseId);

        // Til sidst selve rapport-objektet til formular-binding
        model.addAttribute("damageReport", report);

        // Render samme Thymeleaf-view i alle cases
        return "damageDepartmentHTML/damageReportForm";
    }


    /* -------------------------- NEW (kun oprette) -------------------------- */

    /** Opretter NY rapport for bilens lease (kun hvis ingen rapport findes). */
    @GetMapping("/new")
    public String newDamageReport(@RequestParam(name = "vehicleId", required = false) String vehicleIdStr,
                                  Model model) {

        Integer vehicleId = parseIntOrNull(vehicleIdStr);
        Integer leaseId = (vehicleId == null)
                ? null
                : leaseContractRepo.findLeaseIdByVehicleId(vehicleId).orElse(null);

        if (leaseId == null) {
            // Ingen lease → kan ikke oprette rapport
            model.addAttribute("vehicleId", vehicleId);
            model.addAttribute("noLeaseForVehicle", vehicleId != null);
            model.addAttribute("noReport", true);

            DamageReport empty = new DamageReport();
            empty.setLateReturn(false);
            empty.setPaidStatus(false);
            empty.setDamageItems(new ArrayList<>());
            ensureOneBlankRow(empty);

            model.addAttribute("damageReport", empty);
            return "damageDepartmentHTML/damageReportForm";
        }

        // Hvis rapport allerede findes → vis den i stedet
        DamageReport existing = damageReportService.findByLeaseIdWithItems(leaseId);
        if (existing != null) {
            return "redirect:/damageReportForm/view?vehicleId=" + vehicleId;
        }

        // Opret ny tom rapport bundet til lease
        DamageReport report = new DamageReport();
        report.setLateReturn(false);
        report.setPaidStatus(false);
        report.setLeasingContractId(leaseId);
        report.setTotalKm(0);
        report.setDamageItems(new ArrayList<>());
        ensureOneBlankRow(report);

        damageReportService.recalc(report);

        loadLeaseInfo(leaseId, model, report.getTotalKm());
        model.addAttribute("vehicleId", vehicleId);
        model.addAttribute("damageReport", report);
        return "damageDepartmentHTML/damageReportForm";
    }


    /* -------------------------- SAVE -------------------------- */

    /** Gemmer rapporten og redirecter tilbage til /view for samme vehicleId. */
    @PostMapping("/save")
    public String saveDamageReport(@ModelAttribute DamageReport damageReport,
                                   @RequestParam(name="deleteIds", required=false) List<Integer> deleteIds,
                                   @RequestParam(name="deleteNewIndexes", required=false) List<Integer> deleteNewIndexes,
                                   Model model) {

        // Sikr at boolean-felter ikke er null, hvis radio-knapperne ikke er valgt
        if (damageReport.getLateReturn() == null) {
            damageReport.setLateReturn(false);
        }
        if (damageReport.getPaidStatus() == null) {
            damageReport.setPaidStatus(false);
        }

        boolean hasErrors = false;

        // Valider kilometertal
        if (!ValidationUtil.isNullOrNonNegativeIntegerWithMaxDigits(damageReport.getTotalKm(), 6)) {
            model.addAttribute("kmError", "Kilometertal skal være 0 eller større og må maks have 6 cifre.");
            hasErrors = true;
        }

        // Valider priser på skader
        if (damageReport.getDamageItems() != null) {
            for (DamageItem it : damageReport.getDamageItems()) {
                if (it != null && it.getDamageItemPrice() != null) {
                    if (!ValidationUtil.isNonNegativeWithMaxDigits(it.getDamageItemPrice(), 6, 2)) {
                        model.addAttribute("priceError", "Pris må ikke være negativ og må maks have 6 cifre før komma og 2 decimaler.");
                        hasErrors = true;
                        break;
                    }
                }
            }
        }

        // Håndter slet-markeringer (hvis de findes i formen)
        if (damageReport.getDamageItems() != null) {
            // 1) Fjern eksisterende items markeret til sletning (via id)
            if (deleteIds != null && !deleteIds.isEmpty()) {
                damageReport.getDamageItems().removeIf(it ->
                        it.getDamageItemId() != null && deleteIds.contains(it.getDamageItemId()));
            }

            // 2) Fjern nye (id==null) markeret via indeks
            if (deleteNewIndexes != null && !deleteNewIndexes.isEmpty()) {
                // Fjern fra højeste indeks for at undgå re-indeksering undervejs
                deleteNewIndexes.stream()
                        .sorted(Comparator.reverseOrder())
                        .forEach(idx -> {
                            if (idx >= 0 && idx < damageReport.getDamageItems().size()) {
                                DamageItem it = damageReport.getDamageItems().get(idx);
                                if (it.getDamageItemId() == null) { // kun “nye” rækker
                                    damageReport.getDamageItems().remove((int) idx);
                                }
                            }
                        });
            }

            // 3) Filtrér tomme rækker fra (ingen id, ingen beskrivelse, ingen pris)
            damageReport.setDamageItems(
                    damageReport.getDamageItems().stream()
                            .filter(it -> {
                                boolean hasId    = it.getDamageItemId() != null;
                                boolean hasText  = it.getDescription() != null && !it.getDescription().isBlank();
                                boolean hasPrice = it.getDamageItemPrice() != null;
                                return hasId || hasText || hasPrice;
                            })
                            .toList()
            );
        }

        // Ved valideringsfejl → behold lease/bil-info, recalc og retur til samme view
        if (hasErrors) {
            Integer leaseId = damageReport.getLeasingContractId();
            if (leaseId != null) {
                loadLeaseInfo(leaseId, model, damageReport.getTotalKm());
                leaseContractRepo.findOptionalByLeaseId(leaseId)
                        .ifPresent(lc -> model.addAttribute("vehicleId", lc.getVehicleId()));
            }
            damageReportService.recalc(damageReport); // tager også højde for lateReturn
            ensureOneBlankRow(damageReport);          // så brugeren stadig har en tom række at udfylde
            model.addAttribute("damageReport", damageReport);
            return "damageDepartmentHTML/damageReportForm";
        }



        // Gem og redirect til /view for samme vehicle
        DamageReport saved = damageReportService.processAndSaveDamageReport(damageReport);
        Integer leaseId = saved.getLeasingContractId();

        Integer vehicleIdForRedirect = null;
        if (leaseId != null) {
            vehicleIdForRedirect = leaseContractRepo.findOptionalByLeaseId(leaseId)
                    .map(LeaseContract::getVehicleId)
                    .orElse(null);
        }

        return (vehicleIdForRedirect != null)
                ? "redirect:/damageReportForm/view?vehicleId=" + vehicleIdForRedirect
                : "redirect:/damageReportForm/view";
    }


    /* -------------------------- Hjælpere -------------------------- */

    /** Lægger leasing- og bil-info i modellen og beregner felter til visning. */
    private void loadLeaseInfo(Integer leaseId, Model model, Integer totalKmInput) {
        LeaseContract lease = leaseContractRepo.findOptionalByLeaseId(leaseId).orElse(null);
        if (lease == null) return;

        Car car = carRepo.findById(lease.getVehicleId()).orElse(null);
        int mileage = (car != null) ? car.getMileage() : 0;

        int drivenKm = damageReportService.calculateDrivenKm(totalKmInput, mileage);
        double extraKmPrice = damageReportService.calculateExtraKmPrice(totalKmInput, mileage, lease.getSubscription());

        model.addAttribute("leaseId", lease.getLeasingContractId());
        model.addAttribute("vehicleId", lease.getVehicleId());
        model.addAttribute("renterId", lease.getRenterId());
        model.addAttribute("drivenKm", drivenKm);
        model.addAttribute("extraKmPrice", extraKmPrice);
    }

    /** Giv altid brugeren én tom skaderække at skrive i (kun i view-modellen, ikke DB). */
    private void ensureOneBlankRow(DamageReport report) {
        if (report.getDamageItems() == null) {
            report.setDamageItems(new ArrayList<>());
        }
        if (report.getDamageItems().isEmpty()) {
            report.getDamageItems().add(new DamageItem());
            return;
        }
        DamageItem last = report.getDamageItems().get(report.getDamageItems().size() - 1);
        boolean lastIsBlank = (last.getDamageItemId() == null)
                && (last.getDescription() == null || last.getDescription().isBlank())
                && (last.getDamageItemPrice() == null);
        if (!lastIsBlank) {
            report.getDamageItems().add(new DamageItem());
        }
    }

    /** Parse Integer fra String; returnér null ved tom/ugyldig værdi. */
    private Integer parseIntOrNull(String s) {
        if (s == null || s.isBlank()) return null;
        try { return Integer.valueOf(s.trim()); }
        catch (NumberFormatException e) { return null; }
    }

    /** Tilbage til oversigten (bevarer din rute). */
    @GetMapping
    public String overview(Model model) {
        return "damageDepartmentHTML/damageDepartment";
    }
}
