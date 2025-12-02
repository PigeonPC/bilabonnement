package com.example.bilabonnement.dataregistration.repository;

import com.example.bilabonnement.dataregistration.model.LeaseContract;
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
        String sql = "SELECT * FROM LeaseContract";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class); //Kan ændre fra first_name til firstName
        return template.query(sql, rowMapper); //Laver en List<LeaseContract med objekter
    }

    //Henter alle LeaseContracts hvor ApprovedDate er NULL = Altså hent alle bookinger, der ikke er blevet approved
    public List<LeaseContract> fetchAllBookings() {
        String sql = "SELECT * FROM LeaseContract WHERE approvedDate IS NULL";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rowMapper);
    }

    public LeaseContract findContractByLeasingContractID(int leasingContractID) {
        String
    }






}
