package com.example.bilabonnement.dataRegistration.model;

import com.example.bilabonnement.dataRegistration.CarStatus;
import com.example.bilabonnement.dataRegistration.EquipmentLevel;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @Column(name = "chassis_number")
    private String chassisNumber;

    private String brand;
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_level")
    private EquipmentLevel equipmentLevel;

    @Column(name = "steel_price")
    private Double steelPrice;

    @Column(name = "registration_tax")
    private Double registrationTax;

    @Column(name = "co2_emission")
    private Integer co2Emission;

    private Integer mileage;

    @Column(name = "leasing_code")
    private String leasingCode;

    @Column(name = "irk_code")
    private String irkCode;

    @Column(name = "date_of_purchase")
    private LocalDate dateOfPurchase;

    @Column(name = "purchase_price")
    private Double purchasePrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status")
    private CarStatus carStatus;

    @Column(name = "status_change_timestamp")
    private LocalDateTime statusChangeTimestamp;


    // ------------------------------
    // GETTERS & SETTERS
    // ------------------------------

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
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

    public EquipmentLevel getEquipmentLevel() {
        return equipmentLevel;
    }

    public void setEquipmentLevel(EquipmentLevel equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public Double getSteelPrice() {
        return steelPrice;
    }

    public void setSteelPrice(Double steelPrice) {
        this.steelPrice = steelPrice;
    }

    public Double getRegistrationTax() {
        return registrationTax;
    }

    public void setRegistrationTax(Double registrationTax) {
        this.registrationTax = registrationTax;
    }

    public Integer getCo2Emission() {
        return co2Emission;
    }

    public void setCo2Emission(Integer co2Emission) {
        this.co2Emission = co2Emission;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
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

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public LocalDateTime getStatusChangeTimestamp() {
        return statusChangeTimestamp;
    }

    public void setStatusChangeTimestamp(LocalDateTime statusChangeTimestamp) {
        this.statusChangeTimestamp = statusChangeTimestamp;
    }
}
