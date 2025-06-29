package com.ifs.fleetmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "maintenance_records")
@EntityListeners(AuditingEntityListener.class)
public class MaintenanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "maintenance_seq")
    @SequenceGenerator(name = "maintenance_seq", sequenceName = "maintenance_sequence", allocationSize = 1)
    private Long id;

    @NotNull(message = "Maintenance date is required")
    private LocalDateTime maintenanceDate;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Maintenance type is required")
    private MaintenanceType maintenanceType;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Technician name is required")
    private String technicianName;

    @NotNull(message = "Cost is required")
    private Double cost;

    @Enumerated(EnumType.STRING)
    private MaintenanceStatus status;

    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public MaintenanceRecord() {}

    public MaintenanceRecord(LocalDateTime maintenanceDate, MaintenanceType maintenanceType, 
                           String description, String technicianName, Double cost, 
                           MaintenanceStatus status, Aircraft aircraft) {
        this.maintenanceDate = maintenanceDate;
        this.maintenanceType = maintenanceType;
        this.description = description;
        this.technicianName = technicianName;
        this.cost = cost;
        this.status = status;
        this.aircraft = aircraft;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getMaintenanceDate() { return maintenanceDate; }
    public void setMaintenanceDate(LocalDateTime maintenanceDate) { this.maintenanceDate = maintenanceDate; }

    public MaintenanceType getMaintenanceType() { return maintenanceType; }
    public void setMaintenanceType(MaintenanceType maintenanceType) { this.maintenanceType = maintenanceType; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTechnicianName() { return technicianName; }
    public void setTechnicianName(String technicianName) { this.technicianName = technicianName; }

    public Double getCost() { return cost; }
    public void setCost(Double cost) { this.cost = cost; }

    public MaintenanceStatus getStatus() { return status; }
    public void setStatus(MaintenanceStatus status) { this.status = status; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

enum MaintenanceType {
    ROUTINE, SCHEDULED, EMERGENCY, INSPECTION, REPAIR, OVERHAUL
}

enum MaintenanceStatus {
    SCHEDULED, IN_PROGRESS, COMPLETED, CANCELLED
}