package com.example.bilabonnement.dataRegistration.service;

import com.example.bilabonnement.dataRegistration.model.BookingDetailView;
import com.example.bilabonnement.dataRegistration.model.BookingTableView;
import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import com.example.bilabonnement.dataRegistration.repository.LeaseContractRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseContractService {

   //ikke autowired?
    private final LeaseContractRepo leaseContractRepo;

    public LeaseContractService(LeaseContractRepo leaseContractRepo) {
        this.leaseContractRepo = leaseContractRepo;
    }

    public List<LeaseContract> fetchAllLeaseContracts() {
        return leaseContractRepo.fetchAllLeaseContracts();
    }

    public List<LeaseContract> fetchAllBookings() {
        return leaseContractRepo.fetchAllBookings();
    }

    public LeaseContract findContractByLeasingContractID(int leasingContractId) {
        return leaseContractRepo.findContractByLeasingContractID(leasingContractId);
    }

    public LeaseContract findContractByRenterID(int renterId) {
        return leaseContractRepo.findContractByRenterID(renterId);
    }

    public LeaseContract findContractByVehicleID(int vehicleId) {
        return leaseContractRepo.findContractByVehicleID(vehicleId);
    }

    public boolean approveLeaseContractByID(int leasingContractId) {
        return leaseContractRepo.approveLeaseContractByID(leasingContractId);
    }

    public List<BookingTableView> fetchAllBookingsWithRenterNameAndCarModel() {
        return leaseContractRepo.fetchAllBookingsWithRenterNameAndCarModel();
    }

    public BookingDetailView fetchBookingDetailByIdPlusCustomerAndCar(int leasingContractId) {
        return leaseContractRepo.fetchBookingDetailByIdPlusCustomerAndCar(leasingContractId);
    }



}
