package com.example.bilabonnement.damageDepartment.forms.damageReportForm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "damage_items")
public class DamageItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "damage_item_id")
    private Integer damageItemId;

    @Column(name = "description")
    private String description;

    @Column(name = "damage_item_price", precision = 12, scale = 2)
    private BigDecimal damageItemPrice;

    // Child → Parent (kræver at parent er managed)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "damage_report_id", nullable = false)
    private DamageReport damageReport;

    // Getters/setters
    public Integer getDamageItemId() { return damageItemId; }
    public void setDamageItemId(Integer damageItemId) { this.damageItemId = damageItemId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getDamageItemPrice() { return damageItemPrice; }
    public void setDamageItemPrice(BigDecimal damageItemPrice) { this.damageItemPrice = damageItemPrice; }
    public DamageReport getDamageReport() { return damageReport; }
    public void setDamageReport(DamageReport damageReport) { this.damageReport = damageReport; }
}
