package com.example.bilabonnement.damageDepartment.forms.damageReportForm.service;

import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageItem;
import com.example.bilabonnement.damageDepartment.forms.damageReportForm.model.DamageReport;

import com.example.bilabonnement.damageDepartment.forms.damageReportForm.repository.DamageItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DamageItemService {

    @Autowired
    private DamageItemRepository damageItemRepository;


    // Hent alle items til en report
    public List<DamageItem> getItemsByReport(Integer reportId) {
        return damageItemRepository.findByReportId(reportId);
    }


    // Slet alle items for en report
    public void deleteItemsByReport(Integer reportId) {
        damageItemRepository.deleteByReportId(reportId);
    }


    // Gem ét item
    public DamageItem saveItem(DamageItem item, DamageReport parent) {
        item.setDamageReport(parent);
        damageItemRepository.save(item);
        return item;
    }


    // Gem liste af items
    public void saveItems(List<DamageItem> items, DamageReport parent) {
        for (DamageItem item : items) {
            item.setDamageReport(parent);
            damageItemRepository.save(item);
        }
    }
}

