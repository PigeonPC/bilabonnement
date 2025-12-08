package com.example.bilabonnement.dataRegistration.model;

import com.example.bilabonnement.dataRegistration.EquipmentLevel;
import com.example.bilabonnement.dataRegistration.model.LeaseContract.SubscriptionType;

import java.util.Date;

public class LeaseContractDetailView {

    // ---------- LEASE CONTRACT ----------
    private int leasingContractId;          // lc.leasing_contract_id
    private String leasingContractTerms;    // lc.leasing_contract_terms
    private Date leaseContractDate;         // lc.lease_contract_date
    private Date startDate;                 // lc.start_date
    private Date endDate;                   // lc.end_date
    private double rentalPrice;             // lc.rental_price
    private SubscriptionType subscription;  // lc.subscription
    private Date approvedDate;              // lc.approved_date
    private Date depositPayedDate;          // lc.deposit_payed_date
    private Date fullAmountPayedDate;       // lc.full_amount_payed_date
    private int renterId;                   // lc.renter_id / r.renter_id AS renterId
    private int vehicleId;                  // lc.vehicle_id / car.vehicle_id

    // ---------- CUSTOMER ----------
    private String customerName;            // CONCAT(c.first_name, ' ', c.last_name) AS customerName
    private int customerId;                 // c.customer_id
    private String firstName;               // c.first_name
    private String lastName;                // c.last_name
    private String phone;                   // c.phone
    private String licenseNumber;           // c.license_number
    private String email;                   // c.email
    private String address;                 // c.address
    private int zip;                        // c.zip
    private String floor;                   // c.floor
    private String country;                 // c.country

    // ---------- RENTER ----------
    private String creditScore;             // r.credit_score
    private String ssn;                     // r.ssn

    // ---------- CAR ----------
    private String carModel;                // CONCAT(car.brand, ' ', car.model) AS carModel
    private String brand;                   // car.brand
    private String model;                   // car.model
    private String chassisNumber;           // car.chassis_number
    private EquipmentLevel equipmentLevel;  // car.equipment_level
    private int mileage;                    // car.mileage
    private double steelPrice;              // car.steel_price
    private double registrationTax;         // car.registration_tax
    private int co2Emission;                // car.co2_emission
    private String leasingCode;             // car.leasing_code
    private String irkCode;                 // car.irk_code
    private Date dateOfPurchase;            // car.date_of_purchase
    private double purchasePrice;           // car.purchase_price

    // ---------- STATUS HISTORY (seneste) ----------
    private String lastStatus;          // sh.status
    private Date lastStatusTimestamp;   // sh.timestamp

    // ---------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------

    // LEASE CONTRACT

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


    // CUSTOMER

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
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


    // RENTER

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public Date getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(Date dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

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