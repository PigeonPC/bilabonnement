package com.example.bilabonnement.businessdevelopers.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BusinessDevRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    //Vis antallet af rækker fra tabellen LeaseContract, hvor der er en approvedDate (lejekontrakten er registreret) og
    //hvor dags dato er mellem startTime og endTime:
    //

    public int getTotalRentedCars(){
        String sql = """
                SELECT COUNT(*) FROM Car WHERE car_status ='RENTED'""";

        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result != null ? result : 0;

    }

    //Vis købsværdien (purchasePrice) fra Car tabellen gennem LeaseContract-tabellen hvor dags dato ligger i mellem startTime og endTime:
    public BigDecimal getTotalCarValueOfRentedCars(){
        String sql= """
                SELECT SUM(purchasePrice) FROM Car WHERE car_status = 'RENTED'""";

        BigDecimal result = jdbcTemplate.queryForObject(sql, BigDecimal.class);
        return result != null ? result : BigDecimal.ZERO;
    }

    




}
