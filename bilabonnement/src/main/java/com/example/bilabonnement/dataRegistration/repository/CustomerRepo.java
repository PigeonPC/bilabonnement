package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.Customer;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomerRepo {

    private final JdbcTemplate template;

    public CustomerRepo(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<Customer> findById(int customerId) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        RowMapper<Customer> rm = new BeanPropertyRowMapper<>(Customer.class);
        var result = template.query(sql, rm, customerId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
