package com.example.bilabonnement.dataRegistration.model;

import com.example.bilabonnement.dataRegistration.EquipmentLevel;
import java.util.Date;

public class BookingDetailView {

    // ---------- lease_contracts ----------
    private int leasingContractId;
    private Date leaseContractDate;
    private Date startDate;
    private Date endDate;
    private double rentalPrice;
    private LeaseContract.SubscriptionType subscription;
    private Date approvedDate;

    // ---------- customers ----------
    private String customerName;     // CONCAT(first_name, ' ', last_name)
    private String email;
    private String phone;
    private String address;
    private int zip;
    private String country;

    // ---------- cars ----------
    private String carModel;         // CONCAT(brand, ' ', model)
    private String chassisNumber;
    private int mileage;
    private EquipmentLevel equipmentLevel;


    // ---------------------------------------------------
    // GETTERS & SETTERS – alle matcher kolonnenavne ✔️
    // ---------------------------------------------------

    public int getLeasingContractId() {
        return leasingContractId;
    }

    public void setLeasingContractId(int leasingContractId) {
        this.leasingContractId = leasingContractId;
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

    public LeaseContract.SubscriptionType getSubscription() {
        return subscription;
    }

    public void setSubscription(LeaseContract.SubscriptionType subscription) {
        this.subscription = subscription;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }


    // ---------- CUSTOMER FIELDS ----------

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    // ---------- CAR FIELDS ----------

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public EquipmentLevel getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(EquipmentLevel equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }
}
