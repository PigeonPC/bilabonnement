package com.example.bilabonnement.dataRegistration.service;

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.view.BookingTableView;
import com.example.bilabonnement.dataRegistration.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

    private final LeaseContractRepo leaseContractRepo;
    private final RenterRepo renterRepo;
    private final CustomerRepo customerRepo;
    private final CarRepo carRepo;
    private final StatusHistoryRepo statusHistoryRepo;

    public BookingService(LeaseContractRepo leaseContractRepo,
                          RenterRepo renterRepo,
                          CustomerRepo customerRepo,
                          CarRepo carRepo,
                          StatusHistoryRepo statusHistoryRepo) {
        this.leaseContractRepo = leaseContractRepo;
        this.renterRepo = renterRepo;
        this.customerRepo = customerRepo;
        this.carRepo = carRepo;
        this.statusHistoryRepo = statusHistoryRepo;
    }

    public LeaseContract getLeaseContract(int leasingContractId) {
        return leaseContractRepo.findById(leasingContractId)
                .orElseThrow(() -> new IllegalArgumentException("LeaseContract not found: " + leasingContractId));
    }

    public Renter getRenterForLease(LeaseContract leaseContract) {
        return renterRepo.findById(leaseContract.getRenterId())
                .orElseThrow(() -> new IllegalArgumentException("Renter not found: " + leaseContract.getRenterId()));
    }

    public Customer getCustomerForRenter(Renter renter) {
        return customerRepo.findById(renter.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + renter.getCustomerId()));
    }

    public Car getCarForLease(LeaseContract leaseContract) {
        return carRepo.findById(leaseContract.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Car not found: " + leaseContract.getVehicleId()));
    }

    public StatusHistory getLatestStatusForLease(LeaseContract leaseContract) {
        return statusHistoryRepo.findLatestByVehicleId(leaseContract.getVehicleId())
                .orElse(null); // ligesom ved PreSale – må godt være null
    }


    //BOOKING TABEL
    public List<BookingTableView> fetchAllBookingsWithRenterNameAndCarModel() {
        return leaseContractRepo.fetchAllBookingsWithRenterNameAndCarModel();
    }

    //GODKEND BOOKING
    public boolean approveLeaseContractByIdAndUpdateCarStatus(int leasingContractId) {
        return leaseContractRepo.approveLeaseContractByIdAndUpdateCarStatus(leasingContractId);
    }



}
