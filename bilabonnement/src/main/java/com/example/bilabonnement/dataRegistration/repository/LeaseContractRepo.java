package com.example.bilabonnement.dataRegistration.repository;

/**
 * LeaseContractRepo
 *
 * Formål:
 *  - Læse/skriv-adgang til tabellen `lease_contracts`.
 *  - Indeholder både brede fetch-metoder og robuste find-metoder til UI-flowet.
 *  - Robuste hjælpere:
 *      - findLeaseIdByVehicleId(...)   → returnerer Optional<Integer> (seneste lease, LIMIT 1)
 *      - findOptionalByLeaseId(...)    → returnerer Optional<LeaseContract> for lease_id
 *
 * Teknologi:
 *  - JdbcTemplate til SQL; EntityManager beholdes for simpel findById-symmetri, men UI-flowet
 *    bruger de robuste JDBC-hjælpere for at undgå exceptions.
 */

import com.example.bilabonnement.dataRegistration.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

/** Simpelt JPA-opslag (ikke brugt i UI-flowet, men ok at have). */
    public LeaseContract findById(Long id) {
        return entityManager.find(LeaseContract.class, id.intValue());
    }

/** Hent alle lease contracts (både approved og ikke-approved). */
    public List<LeaseContract> fetchAllLeaseContracts() {
        String sql = "SELECT * FROM lease_contracts";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rm);
    }

/** Hent alle bookinger (approved_date IS NULL). */
    public List<LeaseContract> fetchAllBookings() {
        String sql = "SELECT * FROM lease_contracts WHERE approved_date IS NULL";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rm);
    }

    //Henter alle godkendte lejekontrakter
    public List<LeaseContract> fetchAllConfirmedLeaseContracts() {
        String sql = "SELECT * FROM lease_contracts WHERE approved_date IS NOT NULL";
        RowMapper<LeaseContract> rowMapper = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.query(sql, rowMapper);
    }

    /** Slå kontrakt op på leasing_contract_id (kaster exception ved 0 rækker). */
    public LeaseContract findContractByLeasingContractID(int leasingContractId) {
        String sql = "SELECT * FROM lease_contracts WHERE leasing_contract_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, leasingContractId);
    }

/** Slå kontrakt op på renter_id (kaster exception ved 0 rækker). */
    public LeaseContract findContractByRenterID(int renterId) {
        String sql = "SELECT * FROM lease_contracts WHERE renter_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, renterId);
    }

/** Slå kontrakt op på vehicle_id (kaster exception ved 0 eller >1 rækker). */
    public LeaseContract findContractByVehicleID(int vehicleId) {
        String sql = "SELECT * FROM lease_contracts WHERE vehicle_id = ?";
        RowMapper<LeaseContract> rm = new BeanPropertyRowMapper<>(LeaseContract.class);
        return template.queryForObject(sql, rm, vehicleId);
    }

/** Godkend kontrakt (sæt approved_date=NOW()). */
    public boolean approveLeaseContractByID(int leasingContractId) {
        String sql = """
            UPDATE lease_contracts
               SET approved_date = NOW()
             WHERE leasing_contract_id = ?
               AND approved_date IS NULL
            """;
        return template.update(sql, leasingContractId) > 0;
    }

    // Godkend kontrakt ud fra leasing_contract_id = sæt approved_date fra NULL til NOW()
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


    /** Liste med bookinger inkl. kundens navn og bilens model. */
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


/** Hurtigt existence-check (COUNT(*)) – kan bruges i UI til at styre visning. */
    public boolean existsByVehicleId(int vehicleId) {
        String sql = "SELECT COUNT(*) FROM lease_contracts WHERE vehicle_id = ?";
        Integer count = template.queryForObject(sql, Integer.class, vehicleId);
        return count != null && count > 0;
    }


    public BookingDetailView fetchBookingDetailByIdPlusCustomerRenterAndCar(int leasingContractId) {
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

        RowMapper<BookingDetailView> rowMapper = new BeanPropertyRowMapper<>(BookingDetailView.class);
        return template.queryForObject(sql, rowMapper, leasingContractId);
    }


    //Metode der henter en liste af Lease Contracts med Renter navn og Bil til tabel.
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


    // ------------- PRE SALE TABEL OG DETALJEVISNING ------------------- //


    public List<PreSaleTableView> fetchAllPreSaleAgreementsWithCustomerAndCar() {
        String sql = """
        SELECT
            psa.pre_sale_id,
            psa.pre_sale_agreement_date,
            psa.pickup_location,
            psa.date_of_purchase,
            
            CONCAT(c.first_name, ' ', c.last_name) AS customerName,

            CONCAT(car.brand, ' ', car.model) AS carModel

        FROM pre_sale_agreements psa
        JOIN customers c ON psa.customer_id = c.customer_id
        JOIN cars car    ON psa.vehicle_id = car.vehicle_id
        ORDER BY psa.pre_sale_agreement_date DESC
        """;

        RowMapper<PreSaleTableView> rowMapper = new BeanPropertyRowMapper<>(PreSaleTableView.class);
        return template.query(sql, rowMapper);
    }

    public PreSaleDetailView fetchPreSaleDetailByIdPlusCustomerAndCar(int preSaleId) {

        String sql = """
        SELECT
            -- PRE SALE AGREEMENT
            psa.pre_sale_id,
            psa.limited_period,
            psa.pre_sale_agreement_date,
            psa.pickup_location,
            psa.km_limit,
            psa.extra_km_price,
            psa.pre_sale_agreement_terms,
            psa.currency,
            psa.date_of_purchase,
            psa.customer_id,
            psa.vehicle_id,

            -- CUSTOMER
            CONCAT(c.first_name, ' ', c.last_name) AS customerName,
            c.first_name,
            c.last_name,
            c.phone,
            c.email,
            c.address,
            c.zip,
            c.floor,
            c.country,

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
            car.date_of_purchase AS carDateOfPurchase,
            car.purchase_price,

            -- SENESTE STATUS HISTORY
            sh.status AS lastStatus,
            sh.timestamp AS lastStatusTimestamp

        FROM pre_sale_agreements psa
        JOIN customers c ON psa.customer_id = c.customer_id
        JOIN cars car ON psa.vehicle_id = car.vehicle_id

        LEFT JOIN status_histories sh
               ON sh.vehicle_id = car.vehicle_id
              AND sh.timestamp = (
                   SELECT MAX(sh2.timestamp)
                   FROM status_histories sh2
                   WHERE sh2.vehicle_id = car.vehicle_id
              )

        WHERE psa.pre_sale_id = ?
        """;

        RowMapper<PreSaleDetailView> rowMapper =
                new BeanPropertyRowMapper<>(PreSaleDetailView.class);

        return template.queryForObject(sql, rowMapper, preSaleId);
    }





}
