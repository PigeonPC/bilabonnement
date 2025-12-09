package com.example.bilabonnement.dataRegistration.service;

import com.example.bilabonnement.dataRegistration.model.*;
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

    public boolean approveLeaseContractByIdAndUpdateCarStatus(int leasingContractId) {
        return leaseContractRepo.approveLeaseContractByIdAndUpdateCarStatus(leasingContractId);
    }

    public List<BookingTableView> fetchAllBookingsWithRenterNameAndCarModel() {
        return leaseContractRepo.fetchAllBookingsWithRenterNameAndCarModel();
    }

    public BookingDetailView fetchBookingDetailByIdPlusCustomerRenterAndCar(int leasingContractId) {
        return leaseContractRepo.fetchBookingDetailByIdPlusCustomerRenterAndCar(leasingContractId);
    }

    public List<LeaseContractTableView> fetchAllLeaseContractsWithRenterNameAndCarModel() {
        return leaseContractRepo.fetchAllLeaseContractsWithRenterNameAndCarModel();
    }

    public LeaseContractDetailView fetchLeaseContractDetailByIdPlusCustomerRenterAndCar(int leasingContractId) {
        return leaseContractRepo.fetchLeaseContractDetailByIdPlusCustomerRenterAndCar(leasingContractId);
    }

    public List<PreSaleTableView> fetchAllPreSaleAgreementsWithCustomerAndCar() {
        return leaseContractRepo.fetchAllPreSaleAgreementsWithCustomerAndCar();
    }

    public PreSaleDetailView fetchPreSaleDetailByIdPlusCustomerAndCar(int leasingContractId) {
        return leaseContractRepo.fetchPreSaleDetailByIdPlusCustomerAndCar(leasingContractId);
    }

}
