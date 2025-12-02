package com.example.bilabonnement.businessDevelopers.model;

import java.math.BigDecimal;

public class BusinessDevDashboard {

    private int totalRentedCars;
    private BigDecimal totalValueOfRentedCars;
    private int totalReturnsToday;


    public int getTotalRentedCars() {
        return totalRentedCars;
    }

    public void setTotalRentedCars(int totalRentedCars) {
        this.totalRentedCars = totalRentedCars;
    }

    public BigDecimal getTotalValueOfRentedCars() {
        return totalValueOfRentedCars;
    }

    public void setTotalValueOfRentedCars(BigDecimal totalValueOfRentedCars) {
        this.totalValueOfRentedCars = totalValueOfRentedCars;
    }

    public int getTotalReturnsToday() {
        return totalReturnsToday;
    }

    public void setTotalReturnsToday(int totalReturnsToday) {
        this.totalReturnsToday = totalReturnsToday;
    }
}
