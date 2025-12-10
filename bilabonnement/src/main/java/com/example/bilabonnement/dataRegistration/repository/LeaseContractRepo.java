package com.example.bilabonnement.dataRegistration.repository;

/**
 * LeaseContractRepo
 * <p>
 * Formål:
 * - Læse/skriv-adgang til tabellen `lease_contracts`.
 * - Indeholder både brede fetch-metoder og robuste find-metoder til UI-flowet.
 * - Robuste hjælpere:
 * - findLeaseIdByVehicleId(...)   → returnerer Optional<Integer> (seneste lease, LIMIT 1)
 * - findOptionalByLeaseId(...)    → returnerer Optional<LeaseContract> for lease_id
 * <p>
 * Teknologi:
 * - JdbcTemplate til SQL; EntityManager beholdes for simpel findById-symmetri, men UI-flowet
 * bruger de robuste JDBC-hjælpere for at undgå exceptions.
 */

import com.example.bilabonnement.dataRegistration.model.*;
import com.example.bilabonnement.dataRegistration.model.view.*;
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
public class LeaseContractRepo {

    @Autowired
    JdbcTemplate template;

    @PersistenceContext
    private EntityManager entityManager;

    //FindByID der tager imod en INT!
    public Optional<LeaseContract> findById(int leasingContractId) {
        String sql = "SELECT * FROM lease_contracts WHERE leasing_contract_id = ?";

        RowMapper<LeaseContract> rm =
                new BeanPropertyRowMapper<>(LeaseContract.class);

        var result = template.query(sql, rm, leasingContractId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    /**
     * Simpelt JPA-opslag (ikke brugt i UI-flowet, men ok at have).
     */
    public LeaseContract findById(Long id) {
        return entityManager.find(LeaseContract.class, id.intValue());
    }

    /**
     * Hent alle lease contracts (både approved og ikke-approved).
     */
    public List<LeaseContract> fetchAllLeaseContracts() {
        String sql = "SELECT * FROM lease_contracts";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rm);
    }

    /**
     * Hent alle bookinger (approved_date IS NULL).
     */
    public List<LeaseContract> fetchAllBookings() {
        String sql = "SELECT * FROM lease_contracts WHERE approved_date IS NULL";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rm);
    }

    /**
     * Slå kontrakt op på leasing_contract_id (kaster exception ved 0 rækker).
     */
    public LeaseContract findContractByLeasingContractID(int leasingContractId) {
        String sql = "SELECT * FROM lease_contracts WHERE leasing_contract_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, leasingContractId);
    }

    /**
     * Slå kontrakt op på renter_id (kaster exception ved 0 rækker).
     */
    public LeaseContract findContractByRenterID(int renterId) {
        String sql = "SELECT * FROM lease_contracts WHERE renter_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, renterId);
    }

    /**
     * Slå kontrakt op på vehicle_id (kaster exception ved 0 eller >1 rækker).
     */
    public LeaseContract findContractByVehicleID(int vehicleId) {
        String sql = "SELECT * FROM lease_contracts WHERE vehicle_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, vehicleId);
    }


    // Godkend BOOKING ud fra leasing_contract_id = sæt approved_date fra NULL til NOW()
    // og sæt bilens status til RENTED
    public boolean approveLeaseContractByIdAndUpdateCarStatus(int leasingContractId) {

        // 1) Sæt approved_date, men kun hvis den var NULL
        String updateSql = """
                UPDATE lease_contracts
                SET approved_date = NOW()
                WHERE leasing_contract_id = ?
                  AND approved_date IS NULL
                """;

        int rows = template.update(updateSql, leasingContractId);

        // Hvis ingen rækker blev opdateret, så var den måske allerede godkendt
        if (rows == 0) {
            return false;
        }

        // 2) Indsæt ny status-historik for bilen: RENTED
        String insertStatusSql = """
                INSERT INTO status_histories (vehicle_id, status, timestamp)
                SELECT vehicle_id, 'RENTED', NOW()
                FROM lease_contracts
                WHERE leasing_contract_id = ?
                """;

        template.update(insertStatusSql, leasingContractId);

        return true;
    }

    //VIS TABEL MED BOOKINGER plus lidt om customer og bil
    public List<BookingTableView> fetchAllBookingsWithRenterNameAndCarModel() {
        String sql = """
                SELECT
                    lc.leasing_contract_id,
                    lc.lease_contract_date,
                    CONCAT(c.first_name, ' ', c.last_name) AS customerName,
                    CONCAT(car.brand, ' ', car.model)      AS carModel,
                    lc.start_date,
                    lc.end_date,
                    lc.rental_price,
                    lc.subscription
                FROM lease_contracts lc
                JOIN renters   r   ON lc.renter_id  = r.renter_id
                JOIN customers c   ON r.customer_id = c.customer_id
                JOIN cars      car ON lc.vehicle_id = car.vehicle_id
                WHERE lc.approved_date IS NULL
                """;
        RowMapper<BookingTableView> rm = new BeanPropertyRowMapper<>(BookingTableView.class);
        return template.query(sql, rm);
    }


    //VIS TABEL MED LEJEKONTRAKTER plus lidt om customer og bil
    public List<LeaseContractTableView> fetchAllLeaseContractsWithRenterNameAndCarModel() {
        String sql = """
                SELECT
                    lc.leasing_contract_id,
                    lc.approved_date,
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
                WHERE lc.approved_date IS NOT NULL;
                """;
        RowMapper<LeaseContractTableView> rowMapper = new BeanPropertyRowMapper<>(LeaseContractTableView.class);
        return template.query(sql, rowMapper);
    }

    public LeaseContractDetailView fetchLeaseContractDetailByIdPlusCustomerRenterAndCar(int leasingContractId) {
        String sql = """
                SELECT
                    -- LEASE CONTRACT
                    lc.leasing_contract_id,
                    lc.leasing_contract_terms,
                    lc.lease_contract_date,
                    lc.start_date,
                    lc.end_date,
                    lc.rental_price,
                    lc.subscription,
                    lc.approved_date,
                    lc.deposit_payed_date,
                    lc.full_amount_payed_date,
                    lc.renter_id,
                    lc.vehicle_id,
                
                    -- CUSTOMER
                    CONCAT(c.first_name, ' ', c.last_name) AS customerName,
                    c.customer_id,
                    c.first_name,
                    c.last_name,
                    c.phone,
                    c.license_number,
                    c.email,
                    c.address,
                    c.zip,
                    c.floor,
                    c.country,
                
                    -- RENTER
                    r.credit_score,
                    r.ssn,
                
                    -- CAR
                    CONCAT(car.brand, ' ', car.model) AS carModel,
                    car.brand,
                    car.model,
                    car.chassis_number,
                    car.equipment_level,
                    car.mileage,
                    car.steel_price,
                    car.registration_tax,
                    car.co2_emission,
                    car.leasing_code,
                    car.irk_code,
                    car.date_of_purchase,
                    car.purchase_price,
                
                    -- SENESTE STATUS HISTORY
                    sh.status     AS lastStatus,
                    sh.timestamp  AS lastStatusTimestamp
                
                FROM lease_contracts lc
                JOIN renters r   ON lc.renter_id  = r.renter_id
                JOIN customers c ON r.customer_id = c.customer_id
                JOIN cars car    ON lc.vehicle_id = car.vehicle_id
                
                -- seneste status for bilen
                LEFT JOIN status_histories sh
                       ON sh.vehicle_id = car.vehicle_id
                      AND sh.timestamp = (
                            SELECT MAX(sh2.timestamp)
                            FROM status_histories sh2
                            WHERE sh2.vehicle_id = car.vehicle_id
                      )
                
                WHERE lc.leasing_contract_id = ?
                """;

        RowMapper<LeaseContractDetailView> rowMapper = new BeanPropertyRowMapper<>(LeaseContractDetailView.class);
        return template.queryForObject(sql, rowMapper, leasingContractId);
    }


// ---------- ROBUSTE HJÆLPERE TIL UI-FLOWET ----------

    // Hent seneste lease_id for et vehicle (uændret – returnerer kun tal)
    public Optional<Integer> findLeaseIdByVehicleId(int vehicleId) {
        String sql = """
                    SELECT leasing_contract_id
                    FROM lease_contracts
                    WHERE vehicle_id = ?
                    ORDER BY leasing_contract_id DESC
                    LIMIT 1
                """;
        try {
            Integer id = template.queryForObject(sql, Integer.class, vehicleId);
            return Optional.ofNullable(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Hent én lease robust – bemærk UPPER(subscription) AS subscription
    public Optional<LeaseContract> findOptionalByLeaseId(int leaseId) {
        String sql = """
                    SELECT
                        leasing_contract_id,
                        vehicle_id,
                        renter_id,
                        lease_contract_date,
                        start_date,
                        end_date,
                        rental_price,
                        UPPER(subscription) AS subscription,   -- <<=== vigtig
                        approved_date
                    FROM lease_contracts
                    WHERE leasing_contract_id = ?
                    LIMIT 1
                """;
        var rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        var rows = template.query(sql, rm, leaseId);
        return rows.isEmpty() ? Optional.empty() : Optional.of(rows.get(0));
    }


    /**
     * Hurtigt existence-check (COUNT(*)) – kan bruges i UI til at styre visning.
     */
    public boolean existsByVehicleId(int vehicleId) {
        String sql = "SELECT COUNT(*) FROM lease_contracts WHERE vehicle_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, vehicleId);
        return count != null && count > 0;
    }


}
