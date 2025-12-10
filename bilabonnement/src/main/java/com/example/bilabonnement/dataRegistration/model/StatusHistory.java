package com.example.bilabonnement.dataRegistration.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/** Spejler rækken i status_histories (én statusændring for en bil). */
@Entity
@Table(name = "status_histories")
public class StatusHistory {

    public enum Status {
        PURCHASED, PREPARATION_FOR_RENT, READY_FOR_RENT, RENTED,
        RETURNED, PREPARATION_FOR_SALE, READY_FOR_SALE, SOLD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_history_id")
    private Integer statusHistoryId;

    /** Status gemmes som tekst i DB (ENUM … 'RENTED' osv.). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    /** Hvornår status blev sat. */
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    /** Vi kan nøjes med vehicleId som int, det er enklest. */
    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    // --- Gettere/settere ---
    public Integer getStatusHistoryId() { return statusHistoryId; }
    public void setStatusHistoryId(Integer id) { this.statusHistoryId = id; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Integer getVehicleId() { return vehicleId; }
    public void setVehicleId(Integer vehicleId) { this.vehicleId = vehicleId; }
}
