package com.example.bilabonnement.damageDepartment.forms.damageReportForm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "damage_reports")
public class DamageReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_report_id")
    private Integer damageReportId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "total_damage_price")
    private BigDecimal totalDamagePrice;

    @Column(name = "report_date")
    private LocalDate reportDate;

    @Column(name = "late_return")
    private Boolean lateReturn;

    @Column(name = "total_km")
    private Integer totalKm;

    @Column(name = "has_payed")
    private LocalDateTime hasPayed;

    @Column(name = "leasing_contract_id")
    private Integer leasingContractId;

    @OneToMany(mappedBy = "damageReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DamageItem> damageItems = new ArrayList<>();

    // Virtuel boolean til radioknapper
    @Transient
    private Boolean paidStatus;

    public void updatePaidStatusFromDate() {
        this.paidStatus = (this.hasPayed != null);
    }

    // getters/setters
    public Integer getDamageReportId() { return damageReportId; }
    public void setDamageReportId(Integer damageReportId) { this.damageReportId = damageReportId; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    public BigDecimal getTotalDamagePrice() { return totalDamagePrice; }
    public void setTotalDamagePrice(BigDecimal totalDamagePrice) { this.totalDamagePrice = totalDamagePrice; }
    public LocalDate getReportDate() { return reportDate; }
    public void setReportDate(LocalDate reportDate) { this.reportDate = reportDate; }
    public Boolean getLateReturn() { return lateReturn; }
    public void setLateReturn(Boolean lateReturn) { this.lateReturn = lateReturn; }
    public Integer getTotalKm() { return totalKm; }
    public void setTotalKm(Integer totalKm) { this.totalKm = totalKm; }
    public LocalDateTime getHasPayed() { return hasPayed; }
    public void setHasPayed(LocalDateTime hasPayed) { this.hasPayed = hasPayed; }
    public Integer getLeasingContractId() { return leasingContractId; }
    public void setLeasingContractId(Integer leasingContractId) { this.leasingContractId = leasingContractId; }
    public List<DamageItem> getDamageItems() { return damageItems; }
    public void setDamageItems(List<DamageItem> damageItems) { this.damageItems = damageItems; }
    public Boolean getPaidStatus() { return paidStatus; }
    public void setPaidStatus(Boolean paidStatus) { this.paidStatus = paidStatus; }
}
