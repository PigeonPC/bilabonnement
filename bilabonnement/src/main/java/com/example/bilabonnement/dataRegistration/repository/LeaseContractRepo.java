package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LeaseContractRepo {

    @Autowired
    JdbcTemplate template; //Ved hvordan man forbinder til DB, bruger username/password, og MySQL driver.

    //Hent alle LeaseContracts/bookinger, både approved og ikke approved
    public List<LeaseContract> fetchAllLeaseContracts(){
        String sql = "SELECT * FROM leasecontract";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class); //Kan ændre fra first_name til firstName
        return template.query(sql, rowMapper); //Laver en List<LeaseContract med objekter
    }

    //Henter alle LeaseContracts hvor ApprovedDate er NULL = Altså hent alle bookinger, der ikke er blevet approved
    public List<LeaseContract> fetchAllBookings() {
        String sql = "SELECT * FROM leasecontract WHERE approvedDate IS NULL";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rowMapper);
    }

    //Find kontrakt ud fra kontrakt ID
    public LeaseContract findContractByLeasingContractID(int leasingContract_ID) {
        String sql = "SELECT * FROM leasecontract WHERE leasingContract_ID = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        LeaseContract lc = template.queryForObject(sql, rowMapper, leasingContract_ID);
        return lc;
    }

    //Find kontrakt ud fra renter ID
    public LeaseContract findContractByRenterID(int renter_ID) {
        String sql = "SELECT * FROM leasecontract WHERE renter_ID = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        LeaseContract lc = template.queryForObject(sql, rowMapper, renter_ID);
        return lc;
    }

    //Find kontrakt ud fra vehicle ID
    public LeaseContract findContractByVehicleID(int vehicle_ID) {
        String sql = "SELECT * FROM leasecontract WHERE vehicle_ID = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        LeaseContract lc = template.queryForObject(sql, rowMapper, vehicle_ID);
        return lc;
    }

    //Godkend kontrakt ud fra kontraktID = ændre approved date fra NULL til datos
    public boolean approveLeaseContractByID(int leasingContract_ID) {
        String sql = "UPDATE leasecontract " +
                "SET approvedDate = NOW() " +
                "WHERE leasingContract_ID = ? " +
                "AND approvedDate IS NULL";

        return template.update(sql, leasingContract_ID) > 0;
    }











}
