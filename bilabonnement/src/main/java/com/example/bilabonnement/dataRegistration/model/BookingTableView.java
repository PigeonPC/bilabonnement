package com.example.bilabonnement.dataRegistration.model;

import java.util.Date;

//Objekt der bruges til at vise bookings i en tabel, hvor der også vises renters navn og bilens model
public class BookingTableView {


    private int leasingContractId;   // leasing_contract_id
    private Date leaseContractDate;  // lease_contract_date
    private String customerName;     // CONCAT(first_name, ' ', last_name)
    private String carModel;         // CONCAT(brand, ' ', model)
    private Date startDate;          // start_date
    private Date endDate;            // end_date
    private double rentalPrice;      // rental_price
    private String subscription;     // subscription (Limited/Unlimited)


    public BookingTableView() {
    }

    // getters & setters

    public int getLeasingContractId() {
        return leasingContractId;
    }

    public void setLeasingContractId(int leasingContractId) {
        this.leasingContractId = leasingContractId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
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

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }
}
