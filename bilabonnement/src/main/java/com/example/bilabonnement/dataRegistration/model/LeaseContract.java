package com.example.bilabonnement.dataRegistration.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lease_contracts")
public class LeaseContract{

    public enum SubscriptionType {
        LIMITED,
        UNLIMITED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leasing_contract_id")
    private Integer leasingContractId;

    @Column(name = "leasing_contract_terms")
    private String leasingContractTerms;

    @Column(name = "lease_contract_date")
    private Date leaseContractDate;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "rental_price")
    private double rentalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription")
    private SubscriptionType subscription;

    @Column(name = "approved_date")
    private Date approvedDate;

    @Column(name = "deposit_payed_date")
    private Date depositPayedDate;

    @Column(name = "full_amount_payed_date")
    private Date fullAmountPayedDate;

    @Column(name = "renter_id")
    private Integer renterId;

    @Column(name = "vehicle_id")
    private Integer vehicleId;              // vehicle_id


    // TOM KONSTRUKTØR (kræves af BeanPropertyRowMapper)
    public LeaseContract() {

    }


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
