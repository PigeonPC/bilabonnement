package com.example.bilabonnement.businessDeveloper.service;

import com.example.bilabonnement.businessDeveloper.model.BusinessDevDashboard;
import com.example.bilabonnement.businessDeveloper.repository.BusinessDevRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessDevService {

    @Autowired
    BusinessDevRepository devRepo;

    public BusinessDevDashboard getDashboard(){
        BusinessDevDashboard dashboard = new BusinessDevDashboard();

        //hent data:
        int totalRentedCars = devRepo.getTotalRentedCars();
        //sæt data ind i dashboard objektet:
        dashboard.setTotalRentedCars(totalRentedCars);

        return dashboard;
    }








}
