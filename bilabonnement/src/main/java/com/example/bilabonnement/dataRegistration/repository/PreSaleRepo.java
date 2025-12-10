package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.PreSaleAgreement;
import com.example.bilabonnement.dataRegistration.model.view.PreSaleTableView;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PreSaleRepo {

    private final JdbcTemplate template;

    public PreSaleRepo(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<PreSaleAgreement> findById(int preSaleId) {
        String sql = "SELECT * FROM pre_sale_agreements WHERE pre_sale_id = ?";

        RowMapper<PreSaleAgreement> rm =
                new BeanPropertyRowMapper<>(PreSaleAgreement.class);

        var result = template.query(sql, rm, preSaleId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }


    public List<PreSaleTableView> fetchAllPreSaleAgreementsWithCustomerAndCar() {
        String sql = """
        SELECT
            psa.pre_sale_id,
            psa.pre_sale_agreement_date,
            psa.pickup_location,
            psa.date_of_purchase,
            CONCAT(c.first_name, ' ', c.last_name) AS customerName,
            CONCAT(car.brand, ' ', car.model)      AS carModel
        FROM pre_sale_agreements psa
        JOIN customers c ON psa.customer_id = c.customer_id
        JOIN cars car    ON psa.vehicle_id = car.vehicle_id
        ORDER BY psa.pre_sale_agreement_date DESC
        """;

        RowMapper<PreSaleTableView> rowMapper =
                new BeanPropertyRowMapper<>(PreSaleTableView.class);

        return template.query(sql, rowMapper);
    }



}