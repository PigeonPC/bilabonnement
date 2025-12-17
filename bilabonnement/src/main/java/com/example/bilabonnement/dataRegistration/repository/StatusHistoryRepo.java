package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.CarStatus;
import com.example.bilabonnement.dataRegistration.model.StatusHistory;
import org.springframework.dao.EmptyResultDataAccessException;
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


// Hent SENESTE status for en bil (vehicle_id) ud fra timestamp.
// Returnerer Optional.empty() hvis der ikke findes historik.

    public Optional<CarStatus> findLatestStatusForVehicle(int vehicleId) {
        String sql = """
            SELECT status
            FROM status_histories
            WHERE vehicle_id = ?
            ORDER BY timestamp DESC
            LIMIT 1
        """;
        try {
            String status = template.queryForObject(sql, String.class, vehicleId);
            return Optional.ofNullable(status).map(CarStatus::valueOf);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
