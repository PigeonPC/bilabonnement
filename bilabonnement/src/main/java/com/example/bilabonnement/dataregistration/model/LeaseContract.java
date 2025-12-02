package com.example.bilabonnement.dataregistration.model;

import java.util.Date;

public class LeaseContract {

    public enum SubscriptionType {
        LIMITED,
        UNLIMITED
    }

    private int leasingContract_ID;          // PK
    private String leasingContractTerms;     // TEXT
    private Date leaseContractDate;          // DATE
    private Date startDate;                  // DATE
    private Date endDate;                    // DATE
    private double rentalPrice;              // DECIMAL(10,2)
    private SubscriptionType subscription;   // ENUM
    private Date approvedDate;               // DATETIME
    private Date depositPayedDate;           // DATETIME
    private Date fullAmountPayedDate;        // DATETIME
    private int renter_ID;                   // FK
    private int vehicle_ID;                  // FK


    // Tom konstruktør
    public LeaseContract() {
    }

    // Konstruktør uden ID'er????? Måske skal de med, fordi man kan få den til at blive udfyldt automatisk?
    //Skal man evt. bruge de to FK'er
    public LeaseContract(String leasingContractTerms,
                         Date leaseContractDate,
                         Date startDate,
                         Date endDate,
                         double rentalPrice,
                         SubscriptionType subscription,
                         Date approvedDate,
                         Date depositPayedDate,
                         Date fullAmountPayedDate) {

        this.leasingContractTerms = leasingContractTerms;
        this.leaseContractDate = leaseContractDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.rentalPrice = rentalPrice;
        this.subscription = subscription;
        this.approvedDate = approvedDate;
        this.depositPayedDate = depositPayedDate;
        this.fullAmountPayedDate = fullAmountPayedDate;
    }



    public int getLeasingContract_ID() {
        return leasingContract_ID;
    }

    public void setLeasingContract_ID(int leasingContract_ID) {
        this.leasingContract_ID = leasingContract_ID;
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

    public int getRenter_ID() {
        return renter_ID;
    }

    public void setRenter_ID(int renter_ID) {
        this.renter_ID = renter_ID;
    }

    public int getVehicle_ID() {
        return vehicle_ID;
    }

    public void setVehicle_ID(int vehicle_ID) {
        this.vehicle_ID = vehicle_ID;
    }
}
