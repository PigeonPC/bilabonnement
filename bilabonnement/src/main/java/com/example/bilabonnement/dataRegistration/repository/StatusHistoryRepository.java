package com.example.bilabonnement.dataRegistration.repository;

import com.example.bilabonnement.dataRegistration.CarStatus;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class StatusHistoryRepository {

    private final JdbcTemplate jdbc;

    // Almindelig constructor-injektion
    public StatusHistoryRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    /**
     * Hent SENESTE status for en bil (vehicle_id) ud fra timestamp.
     * Returnerer Optional.empty() hvis der ikke findes historik.
     */
    public Optional<CarStatus> findLatestStatusForVehicle(int vehicleId) {
        String sql = """
            SELECT status
            FROM status_histories
            WHERE vehicle_id = ?
            ORDER BY timestamp DESC
            LIMIT 1
        """;
        try {
            String status = jdbc.queryForObject(sql, String.class, vehicleId);
            return Optional.ofNullable(status).map(CarStatus::valueOf);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    /**
     * Tæl biler hvor DEN NYESTE status (maks timestamp pr. bil) er = status.
     * Brugbar til dashboards/KPI’er.
     */

    public int countByLatestStatus(CarStatus status) {
        String sql = """
            SELECT COUNT(*) 
            FROM status_histories sh
            WHERE sh.timestamp = (
                SELECT MAX(sh2.timestamp)
                FROM status_histories sh2
                WHERE sh2.vehicle_id = sh.vehicle_id
            )
            AND sh.status = ?
        """;
        Integer n = jdbc.queryForObject(sql, Integer.class, status.name());
        return n != null ? n : 0;
    }
}