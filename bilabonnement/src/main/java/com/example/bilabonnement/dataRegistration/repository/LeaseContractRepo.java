package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.BookingTableView;
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
    JdbcTemplate template; // Ved hvordan man forbinder til DB, bruger username/password, og MySQL driver.

    // Hent alle lease_contracts (både approved og ikke approved)
    public List<LeaseContract> fetchAllLeaseContracts() {
        String sql = "SELECT * FROM lease_contracts";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rowMapper);
    }

    // Hent alle lease_contracts hvor approved_date IS NULL = bookinger der ikke er approved endnu
    public List<LeaseContract> fetchAllBookings() {
        String sql = "SELECT * FROM lease_contracts WHERE approved_date IS NULL";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rowMapper);
    }

    // Find kontrakt ud fra leasing_contract_id
    public LeaseContract findContractByLeasingContractID(int leasingContractId) {
        String sql = "SELECT * FROM lease_contracts WHERE leasing_contract_id = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rowMapper, leasingContractId);
    }

    // Find kontrakt ud fra renter_id
    public LeaseContract findContractByRenterID(int renterId) {
        String sql = "SELECT * FROM lease_contracts WHERE renter_id = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rowMapper, renterId);
    }

    // Find kontrakt ud fra vehicle_id
    public LeaseContract findContractByVehicleID(int vehicleId) {
        String sql = "SELECT * FROM lease_contracts WHERE vehicle_id = ?";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rowMapper, vehicleId);
    }

    // Godkend kontrakt ud fra leasing_contract_id = sæt approved_date fra NULL til NOW()
    public boolean approveLeaseContractByID(int leasingContractId) {
        String sql = "UPDATE lease_contracts " +
                "SET approved_date = NOW() " +
                "WHERE leasing_contract_id = ? " +
                "AND approved_date IS NULL";

        return template.update(sql, leasingContractId) > 0;
    }

    public List<BookingTableView> fetchAllBookingsWithRenterNameAndCarModel() {
        String sql = """
                SELECT
                    lc.leasing_contract_id,
                    lc.lease_contract_date,
                    CONCAT(c.first_name, ' ', c.last_name) AS customerName,
                    CONCAT(car.brand, ' ', car.model) AS carModel,
                    lc.start_date,
                    lc.end_date,
                    lc.rental_price,
                    lc.subscription
                FROM lease_contracts lc
                JOIN renters r ON lc.renter_id = r.renter_id
                JOIN customers c ON r.customer_id = c.customer_id
                JOIN cars car ON lc.vehicle_id = car.vehicle_id
                WHERE lc.approved_date IS NULL;
                """;
        RowMapper<BookingTableView> rowMapper = new BeanPropertyRowMapper<>(BookingTableView.class);
        return template.query(sql, rowMapper);
    }
}
