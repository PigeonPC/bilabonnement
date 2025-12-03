package com.example.bilabonnement.damageDepartment.forms.damageReportForm.controller;

import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageItem;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageReport;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.service.DamageReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/damageReportForm")
public class DamageReportController {

    @Autowired private DamageReportService damageReportService;
    @Autowired private LeaseContractRepo leaseContractRepo;
    @Autowired private CarRepo carRepo;

    /** Uden leaseId → tom formular. Med leaseId → hent seneste (med items). */
    @GetMapping("/new")
    public String newDamageReport(@RequestParam(required = false) Integer leaseId, Model model) {
        DamageReport report = null;

        if (leaseId != null) {
            report = damageReportService.findByLeaseIdWithItems(leaseId);
            if (report == null) {
                report = new DamageReport();
                report.setLeasingContractId(leaseId);
                report.setTotalKm(0);
                report.setDamageItems(new ArrayList<>());
            }
            if (report.getDamageItems() == null || report.getDamageItems().isEmpty()) {
                report.getDamageItems().add(new DamageItem());
            }
            report.updatePaidStatusFromDate();

            // ✅ Beregn totals til visning
            damageReportService.recalc(report);

            loadLeaseInfo(leaseId, model, report.getTotalKm());
        }

        if (report == null) {
            report = new DamageReport();
            report.setDamageItems(new ArrayList<>());
            report.getDamageItems().add(new DamageItem());
            // (valgfrit) damageReportService.recalc(report);
        }

        model.addAttribute("damageReport", report);
        return "damageDepartmentHTML/damageReportForm";
    }


    /** GEM → OPDATERER samme rapport (MERGE) når id følger med. */
    @PostMapping("/save")
    public String saveDamageReport(@ModelAttribute DamageReport damageReport) {
        DamageReport saved = damageReportService.processAndSaveDamageReport(damageReport);
        Integer leaseId = saved.getLeasingContractId();
        return (leaseId != null)
                ? "redirect:/damageReportForm/new?leaseId=" + leaseId
                : "redirect:/damageReportForm/new";
    }

    /* — helper til UI — */
    private void loadLeaseInfo(Integer leaseId, Model model, Integer totalKmInput) {
        LeaseContract lease = leaseContractRepo.findById(leaseId.longValue());
        if (lease == null) return;

        Car car = carRepo.findById(lease.getVehicleId()).orElse(null);
        int mileage = (car != null) ? car.getMileage() : 0;

        int drivenKm = damageReportService.calculateDrivenKm(totalKmInput, mileage);
        double extraKmPrice = damageReportService.calculateExtraKmPrice(totalKmInput, mileage, lease.getSubscription());

        model.addAttribute("vehicleId", lease.getVehicleId());
        model.addAttribute("renterId", lease.getRenterId());
        model.addAttribute("drivenKm", drivenKm);
        model.addAttribute("extraKmPrice", extraKmPrice);
    }

    /* Valgfrit: probe til fejlfinding (kan beholdes/kommenteres ud) */
    @GetMapping("/probe")
    @ResponseBody
    public String probe(@RequestParam Integer leaseId) {
        DamageReport r = damageReportService.findByLeaseIdWithItems(leaseId);
        return "leaseId=" + leaseId +
                ", reportId=" + (r==null?null:r.getDamageReportId()) +
                ", items=" + (r==null || r.getDamageItems()==null ? 0 : r.getDamageItems().size()) +
                ", totalKm=" + (r==null?null:r.getTotalKm());
    }
}
