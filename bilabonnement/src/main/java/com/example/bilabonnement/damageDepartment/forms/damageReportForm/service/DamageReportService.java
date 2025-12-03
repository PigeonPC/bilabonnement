package com.example.bilabonnement.damageDepartment.forms.damageReportForm.service;

import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.repository.CarRepo;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageItem;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageReport;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.repository.DamageItemRepository;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.repository.DamageReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DamageReportService {

    @Autowired private DamageReportRepository damageReportRepository;
    @Autowired private DamageItemRepository damageItemRepository;
    @Autowired private LeaseContractRepo leaseContractRepo;
    @Autowired private CarRepo carRepo;

    /** Hent seneste “med items”; fallback til seneste. Hydrér items. */
    public DamageReport findByLeaseIdWithItems(Integer leaseId) {
        Integer id = damageReportRepository.findLatestWithItemsIdByLeaseId(leaseId);
        if (id == null) id = damageReportRepository.findLatestIdByLeaseId(leaseId);
        if (id == null) return null;

        DamageReport dr = damageReportRepository.findWithItemsById(id);
        if (dr == null) dr = damageReportRepository.findById(id);
        return dr;
    }

    public DamageReport findById(Integer id) { return damageReportRepository.findById(id); }

    /* ===== Beregninger ===== */
    public int calculateDrivenKm(Integer totalKm, Integer mileage) {
        if (totalKm == null || mileage == null) return 0;
        return Math.max(0, totalKm - mileage);
    }

    public double calculateExtraKmPrice(Integer totalKm, Integer mileage,
                                        LeaseContract.SubscriptionType subscription) {
        if (totalKm == null || mileage == null || subscription == null) return 0;
        int drivenKm = calculateDrivenKm(totalKm, mileage);
        int freeKmLimit = (subscription == LeaseContract.SubscriptionType.Limited) ? 400 : 1500;
        double pricePerKm = 2.5;
        if (drivenKm <= freeKmLimit) return 0;
        return (drivenKm - freeKmLimit) * pricePerKm;
    }

    private BigDecimal calculateDamageItemSum(List<DamageItem> items) {
        BigDecimal sum = BigDecimal.ZERO;
        if (items != null) {
            for (DamageItem di : items) {
                if (di != null && di.getDamageItemPrice() != null) sum = sum.add(di.getDamageItemPrice());
            }
        }
        return sum;
    }

    public void recalc(DamageReport report) {
        BigDecimal damageSum = calculateDamageItemSum(report.getDamageItems());
        report.setTotalDamagePrice(damageSum);

        Integer leaseId = report.getLeasingContractId();
        LeaseContract lease = (leaseId == null) ? null : leaseContractRepo.findById(leaseId.longValue());
        Car car = (lease != null) ? carRepo.findById(lease.getVehicleId()).orElse(null) : null;

        int mileage = (car != null) ? car.getMileage() : 0;
        LeaseContract.SubscriptionType subscription =
                (lease != null) ? lease.getSubscription() : LeaseContract.SubscriptionType.Limited;

        double extraKmPrice = calculateExtraKmPrice(report.getTotalKm(), mileage, subscription);
        report.setTotalPrice(damageSum.add(BigDecimal.valueOf(extraKmPrice)));
    }

    /* ===== Persistens ===== */
    @Transactional
    public DamageReport saveReport(DamageReport report) {
        // Synk paidStatus <-> hasPayed
        if (Boolean.TRUE.equals(report.getPaidStatus())) {
            if (report.getHasPayed() == null) report.setHasPayed(LocalDateTime.now());
        } else {
            report.setHasPayed(null);
        }

        // 1) Filter tomme placeholder-rækker
        List<DamageItem> items = report.getDamageItems();
        if (items == null) items = new ArrayList<>();
        List<DamageItem> clean = new ArrayList<>();
        for (DamageItem it : items) {
            if (it == null) continue;
            boolean emptyDesc  = it.getDescription() == null || it.getDescription().isBlank();
            boolean emptyPrice = it.getDamageItemPrice() == null;
            if (emptyDesc && emptyPrice) continue;
            clean.add(it);
        }

        // 2) Afbryd cascade før save af parent
        report.setDamageItems(new ArrayList<>());

        // 3) Recalc totals
        recalc(report);

        // 4) Hvis opdatering: slet gamle items
        if (report.getDamageReportId() != null) {
            damageItemRepository.deleteByReportId(report.getDamageReportId());
        }

        // 5) Gem/MERGE selve rapporten (så vi har et managed report + id)
        damageReportRepository.save(report);

        // 6) NULSTIL ID PÅ ITEMS efter sletning → de skal PERSIST’es som nye!
        for (DamageItem it : clean) {
            it.setDamageItemId(null);          // <<< VIGTIG LINJE
            it.setDamageReport(report);
            damageItemRepository.save(it);     // vil kalde em.persist(...)
        }

        // 7) tilbage til UI
        report.setDamageItems(clean);
        return report;
    }


    public DamageReport processAndSaveDamageReport(DamageReport report) { return saveReport(report); }
}
