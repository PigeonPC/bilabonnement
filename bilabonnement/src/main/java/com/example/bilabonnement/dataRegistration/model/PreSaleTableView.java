package com.example.bilabonnement.dataRegistration.model;

import java.util.Date;

public class PreSaleTableView {

    private int preSaleId;                 // pre_sale_id
    private Date preSaleAgreementDate;     // pre_sale_agreement_date
    private String customerName;           // CONCAT(c.first_name, ' ', c.last_name)
    private String carModel;               // CONCAT(car.brand, ' ', car.model)
    private String pickupLocation;         // pickup_location
    private Date dateOfPurchase;           // date_of_purchase

    // getters & setters
    public int getPreSaleId() {
        return preSaleId;
    }

    public void setPreSaleId(int preSaleId) {
        this.preSaleId = preSaleId;
    }

    public Date getPreSaleAgreementDate() {
        return preSaleAgreementDate;
    }

    public void setPreSaleAgreementDate(Date preSaleAgreementDate) {
        this.preSaleAgreementDate = preSaleAgreementDate;
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

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

}
