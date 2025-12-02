package com.example.bilabonnement.businessdevelopers.service;

import com.example.bilabonnement.businessdevelopers.model.BusinessDevDashboard;
import com.example.bilabonnement.businessdevelopers.repository.BusinessDevRepository;
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
