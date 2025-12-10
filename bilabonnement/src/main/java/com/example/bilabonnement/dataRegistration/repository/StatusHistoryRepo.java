package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.model.StatusHistory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StatusHistoryRepo {

    private final JdbcTemplate template;

    public StatusHistoryRepo(JdbcTemplate template) {
        this.template = template;
    }

    public Optional<StatusHistory> findLatestByVehicleId(int vehicleId) {
        String sql = """
            SELECT *
            FROM status_histories
            WHERE vehicle_id = ?
            ORDER BY timestamp DESC
            LIMIT 1
            """;

        RowMapper<StatusHistory> rm = new BeanPropertyRowMapper<>(StatusHistory.class);
        var result = template.query(sql, rm, vehicleId);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }
}
