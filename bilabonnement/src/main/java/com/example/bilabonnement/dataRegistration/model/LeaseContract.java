package com.example.bilabonnement.dataRegistration.model;

import java.util.Date;

public class LeaseContract {

    public enum SubscriptionType {
        Limited,
        Unlimited
    }

    private int leasingContractId;        // leasing_contract_id
    private String leasingContractTerms;  // leasing_contract_terms
    private Date leaseContractDate;       // lease_contract_date
    private Date startDate;               // start_date
    private Date endDate;                 // end_date
    private double rentalPrice;           // rental_price
    private SubscriptionType subscription; // subscription ENUM
    private Date approvedDate;            // approved_date
    private Date depositPayedDate;        // deposit_payed_date
    private Date fullAmountPayedDate;     // full_amount_payed_date
    private int renterId;                 // renter_id
    private int vehicleId;                // vehicle_id

    // TOM KONSTRUKTØR (kræves af BeanPropertyRowMapper)
    public LeaseContract() {}

    // VALGFRI konstruktør
    public LeaseContract(String leasingContractTerms,
                         Date leaseContractDate,
                         Date startDate,
                         Date endDate,
                         double rentalPrice,
                         SubscriptionType subscription,
                         Date approvedDate,
                         Date depositPayedDate,
                         Date fullAmountPayedDate,
                         int renterId,
                         int vehicleId) {

        this.leasingContractTerms = leasingContractTerms;
        this.leaseContractDate = leaseContractDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalPrice = rentalPrice;
        this.subscription = subscription;
        this.approvedDate = approvedDate;
        this.depositPayedDate = depositPayedDate;
        this.fullAmountPayedDate = fullAmountPayedDate;
        this.renterId = renterId;
        this.vehicleId = vehicleId;
    }

    // GETTERS & SETTERS

    public int getLeasingContractId() {
        return leasingContractId;
    }

    public void setLeasingContractId(int leasingContractId) {
        this.leasingContractId = leasingContractId;
    }

    public String getLeasingContractTerms() {
        return leasingContractTerms;
    }

    public void setLeasingContractTerms(String leasingContractTerms) {
        this.leasingContractTerms = leasingContractTerms;
    }

    public Date getLeaseContractDate() {
        return leaseContractDate;
    }

    public void setLeaseContractDate(Date leaseContractDate) {
        this.leaseContractDate = leaseContractDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(double rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public SubscriptionType getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionType subscription) {
        this.subscription = subscription;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Date getDepositPayedDate() {
        return depositPayedDate;
    }

    public void setDepositPayedDate(Date depositPayedDate) {
        this.depositPayedDate = depositPayedDate;
    }

    public Date getFullAmountPayedDate() {
        return fullAmountPayedDate;
    }

    public void setFullAmountPayedDate(Date fullAmountPayedDate) {
        this.fullAmountPayedDate = fullAmountPayedDate;
    }

    public int getRenterId() {
        return renterId;
    }

    public void setRenterId(int renterId) {
        this.renterId = renterId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
