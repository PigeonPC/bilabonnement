package com.example.bilabonnement.dataregistration.service;

import com.example.bilabonnement.dataregistration.model.LeaseContract;
import com.example.bilabonnement.dataregistration.repository.LeaseContractRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class leaseContractService {
    @Autowired
    LeaseContractRepo leaseContractRepo;


    public List<LeaseContract> fetchAllLeaseContracts(){
        return leaseContractRepo.fetchAllLeaseContracts();
    }

    public List<LeaseContract> fetchAllBookings(){
        return leaseContractRepo.fetchAllBookings();
    }

    public LeaseContract findContractByLeasingContractID(int leasingContract_ID) {
        return leaseContractRepo.findContractByLeasingContractID(leasingContract_ID);
    }

    public LeaseContract findContractByRenterID(int renter_ID) {
        return leaseContractRepo.findContractByRenterID(renter_ID);
    }

    public LeaseContract findContractByVehicleID(int vehicle_ID) {
        return leaseContractRepo.findContractByVehicleID(vehicle_ID);
    }

    public boolean approveLeaseContractByID(int leasingContractID) {
        return leaseContractRepo.approveLeaseContractByID(leasingContractID);
    }



}
