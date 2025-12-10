package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.Renter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class RenterRepo {

    private final JdbcTemplate template;

    public RenterRepo(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<Renter> findById(int renterId) {
        String sql = "SELECT * FROM renters WHERE renter_id = ?";

        RowMapper<Renter> rm =
                new BeanPropertyRowMapper<>(Renter.class);

        var result = template.query(sql, rm, renterId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
