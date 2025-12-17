package com.example.bilabonnement.dataRegistration.repository;


// Er et kombineret repository:
// klasse baseret på JPA- og JDBC-metoder til CarView (listevisning, detaljer, status, leaseContractId, approvedDate)


import com.example.bilabonnement.dataRegistration.model.Car;
import com.example.bilabonnement.dataRegistration.model.view.CarView;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepo {

//JPA:

    @PersistenceContext
    private EntityManager entityManager;

//JDBC:

    @Autowired
    private JdbcTemplate template;



//------- JPA-metoder --------

    public Optional<Car> findById(Integer id) {
        return Optional.ofNullable(entityManager.find(Car.class, id));
    }



//------ JDBC-metoder --------

//Hent alle biler fra cars-tabellen:

    public List<CarView> fetchAllCars(){

        String sql = """
                SELECT
                    vehicle_id AS vehicleId,
                    chassis_number AS chassisNumber, 
                    brand,
                    model,
                    equipment_level AS equipmentLevel,
                    steel_price AS steelPrice,
                    registration_tax AS registrationTax,
                    co2_emission AS co2Emission,
                    mileage,
                    leasing_code AS leasingCode,
                    irk_code AS irkCode,
                    date_of_purchase AS dateOfPurchase,
                    purchase_price AS purchasePrice
                FROM cars
                """;
        RowMapper<CarView> rowMapper = new BeanPropertyRowMapper<>(CarView.class);
        return template.query(sql, rowMapper);
    }


//Hent alle biler ud fra status:

    public List<CarView> fetchCarsByStatus(String status){
        String sql = """
                SELECT
                    c.vehicle_id AS vehicleId,
                    c.chassis_number AS chassisNumber,
                    c.brand,
                    c.model,
                    c.equipment_level AS equipmentLevel,
                    c.mileage,
                    h.status AS carStatus
                FROM cars c
                JOIN status_histories h
                    ON h.vehicle_id = c.vehicle_id
                WHERE h.timestamp = (
                    SELECT MAX(h2.timestamp)
                    FROM status_histories h2
                    WHERE h2.vehicle_id = c.vehicle_id
                )
                AND h.status = ?
                """;

        RowMapper<CarView> rowMapper = new BeanPropertyRowMapper<>(CarView.class);
        return template.query(sql, rowMapper, status);
    }

//Vis detaljer om en bil ud fra bilens id:

    public CarView fetchCarById(int vehicleId){
        String sql = """
                SELECT
                    c.vehicle_id AS vehicleId,
                    c.chassis_number AS chassisNumber,
                    c.brand,
                    c.model,
                    c.equipment_level AS equipmentLevel,
                    c.steel_price AS steelPrice,
                    c.registration_tax AS registrationTax,
                    c.co2_emission AS co2Emission,
                    c.mileage,
                    c.leasing_code AS leasingCode,
                    c.irk_code AS irkCode,
                    c.date_of_purchase AS dateOfPurchase,
                    c.purchase_price AS purchasePrice,
                    h.status AS carStatus,
                    lc.leasing_contract_id AS leaseContractId,
                    lc.approved_date AS approvedDate
                FROM cars c
                LEFT JOIN status_histories h
                    ON h.vehicle_id = c.vehicle_id
                AND h.timestamp= (
                    SELECT MAX(h2.timestamp) 
                    FROM status_histories h2 
                    WHERE h2.vehicle_id = c.vehicle_id
                    )
                LEFT JOIN lease_contracts lc
                    ON lc.vehicle_id = c.vehicle_id
                WHERE c.vehicle_id =?
                """;

        RowMapper<CarView> rowMapper = new BeanPropertyRowMapper<>(CarView.class);
        return template.queryForObject(sql, rowMapper, vehicleId);
    }


//Ændre (tilføj) status for en bil i status_histories:

    public boolean insertStatusHistory(int vehicleId, String status){
        String sql = """
                INSERT INTO status_histories (vehicle_id, status, timestamp)
                VALUES(?, ?, Now())
                """;
        return template.update(sql, vehicleId, status)> 0;
    }



}
