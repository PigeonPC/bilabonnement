package com.example.bilabonnement.dataRegistration.model;

import com.example.bilabonnement.dataRegistration.EquipmentLevel;

import java.util.Date;

public class PreSaleDetailView {

    // ---------- PRE SALE AGREEMENT ----------
    private int preSaleId;                    // pre_sale_id
    private boolean limitedPeriod;            // limited_period
    private Date preSaleAgreementDate;        // pre_sale_agreement_date
    private String pickupLocation;            // pickup_location
    private int kmLimit;                      // km_limit
    private double extraKmPrice;              // extra_km_price
    private String preSaleAgreementTerms;     // pre_sale_agreement_terms
    private String currency;                  // currency ENUM
    private Date dateOfPurchase;              // date_of_purchase
    private int customerId;                   // customer_id
    private int vehicleId;                    // vehicle_id

    // ---------- CUSTOMER ----------
    private String customerName;              // CONCAT(first_name + last_name)
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private int zip;
    private String floor;
    private String country;

    // ---------- CAR ----------
    private String carModel;                  // CONCAT(brand + model)
    private String brand;
    private String model;
    private String chassisNumber;
    private EquipmentLevel equipmentLevel;
    private int mileage;
    private double steelPrice;
    private double registrationTax;
    private int co2Emission;
    private String leasingCode;
    private String irkCode;
    private Date carDateOfPurchase;
    private double purchasePrice;

    // ---------- LATEST STATUS ----------
    private String lastStatus;
    private Date lastStatusTimestamp;

    // --------------------------------------------
    // GETTERS & SETTERS
    // --------------------------------------------

    // PRE SALE AGREEMENT
    public int getPreSaleId() {
        return preSaleId;
    }
    public void setPreSaleId(int preSaleId) {
        this.preSaleId = preSaleId;
    }

    public boolean isLimitedPeriod() {
        return limitedPeriod;
    }
    public void setLimitedPeriod(boolean limitedPeriod) {
        this.limitedPeriod = limitedPeriod;
    }

    public Date getPreSaleAgreementDate() {
        return preSaleAgreementDate;
    }
    public void setPreSaleAgreementDate(Date preSaleAgreementDate) {
        this.preSaleAgreementDate = preSaleAgreementDate;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public int getKmLimit() {
        return kmLimit;
    }
    public void setKmLimit(int kmLimit) {
        this.kmLimit = kmLimit;
    }

    public double getExtraKmPrice() {
        return extraKmPrice;
    }
    public void setExtraKmPrice(double extraKmPrice) {
        this.extraKmPrice = extraKmPrice;
    }

    public String getPreSaleAgreementTerms() {
        return preSaleAgreementTerms;
    }
    public void setPreSaleAgreementTerms(String preSaleAgreementTerms) {
        this.preSaleAgreementTerms = preSaleAgreementTerms;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }
    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getCustomerId() {
        return customerId;
    }
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getVehicleId() {
        return vehicleId;
    }
    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }


    // CUSTOMER

    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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

    public String getFloor() {
        return floor;
    }
    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    // CAR

    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }
    public void setModel(String model) {
        this.model = model;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }
    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public EquipmentLevel getEquipmentLevel() {
        return equipmentLevel;
    }
    public void setEquipmentLevel(EquipmentLevel equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public int getMileage() {
        return mileage;
    }
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getSteelPrice() {
        return steelPrice;
    }
    public void setSteelPrice(double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public double getRegistrationTax() {
        return registrationTax;
    }
    public void setRegistrationTax(double registrationTax) {
        this.registrationTax = registrationTax;
    }

    public int getCo2Emission() {
        return co2Emission;
    }
    public void setCo2Emission(int co2Emission) {
        this.co2Emission = co2Emission;
    }

    public String getLeasingCode() {
        return leasingCode;
    }
    public void setLeasingCode(String leasingCode) {
        this.leasingCode = leasingCode;
    }

    public String getIrkCode() {
        return irkCode;
    }
    public void setIrkCode(String irkCode) {
        this.irkCode = irkCode;
    }

    public Date getCarDateOfPurchase() {
        return carDateOfPurchase;
    }
    public void setCarDateOfPurchase(Date carDateOfPurchase) {
        this.carDateOfPurchase = carDateOfPurchase;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    // STATUS

    public String getLastStatus() {
        return lastStatus;
    }
    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public Date getLastStatusTimestamp() {
        return lastStatusTimestamp;
    }
    public void setLastStatusTimestamp(Date lastStatusTimestamp) {
        this.lastStatusTimestamp = lastStatusTimestamp;
    }
}
