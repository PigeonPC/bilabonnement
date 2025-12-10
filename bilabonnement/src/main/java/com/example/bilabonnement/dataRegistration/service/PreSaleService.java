package com.example.bilabonnement.dataRegistration.service;

import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.model.Customer;
import com.example.bilabonnement.dataRegistration.model.PreSaleAgreement;
import com.example.bilabonnement.dataRegistration.model.StatusHistory;
import com.example.bilabonnement.dataRegistration.model.view.PreSaleTableView;
import com.example.bilabonnement.dataRegistration.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreSaleService {

    private final PreSaleRepo preSaleRepo;
    private final CustomerRepo customerRepo;
    private final CarRepo carRepo;
    private final StatusHistoryRepo statusHistoryRepo;

    public PreSaleService(PreSaleRepo preSaleRepo,
                          CustomerRepo customerRepo,
                          CarRepo carRepo,
                          StatusHistoryRepo statusHistoryRepo) {
        this.preSaleRepo = preSaleRepo;
        this.customerRepo = customerRepo;
        this.carRepo = carRepo;
        this.statusHistoryRepo = statusHistoryRepo;
    }

    public PreSaleAgreement getPreSaleAgreement(int preSaleId) {
        return preSaleRepo.findById(preSaleId)
                .orElseThrow(() -> new IllegalArgumentException("PreSaleAgreement not found: " + preSaleId));
    }

    public Customer getCustomerForPreSale(PreSaleAgreement preSaleAgreement) {
        return customerRepo.findById(preSaleAgreement.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + preSaleAgreement.getCustomerId()));
    }

    public Car getCarForPreSale(PreSaleAgreement preSaleAgreement) {
        return carRepo.findById(preSaleAgreement.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + preSaleAgreement.getVehicleId()));
    }

    public StatusHistory getLatestStatusForPreSale(PreSaleAgreement preSaleAgreement) {
        return statusHistoryRepo.findLatestByVehicleId(preSaleAgreement.getVehicleId())
                .orElse(null); // må godt være null
    }


    //PRE SALE TABEL
    public List<PreSaleTableView> fetchAllPreSaleAgreementsWithCustomerAndCar() {
        return preSaleRepo.fetchAllPreSaleAgreementsWithCustomerAndCar();
    }

}
