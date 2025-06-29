package com.ifs.fleetmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "aircraft")
@EntityListeners(AuditingEntityListener.class)
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_seq")
    @SequenceGenerator(name = "aircraft_seq", sequenceName = "aircraft_sequence", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Registration number is required")
    @Column(unique = true, nullable = false)
    private String registrationNumber;

    @NotBlank(message = "Aircraft model is required")
    private String model;

    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;

    @NotNull(message = "Manufacturing year is required")
    private Integer manufacturingYear;

    @NotNull(message = "Maximum capacity is required")
    private Integer maxCapacity;

    @Enumerated(EnumType.STRING)
    private AircraftStatus status;

    @NotNull(message = "Total flight hours is required")
    private Double totalFlightHours;

    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FlightLog> flightLogs;

    @OneToMany(mappedBy = "aircraft", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MaintenanceRecord> maintenanceRecords;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    // Constructors
    public Aircraft() {}

    public Aircraft(String registrationNumber, String model, String manufacturer, 
                   Integer manufacturingYear, Integer maxCapacity, AircraftStatus status, 
                   Double totalFlightHours) {
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.manufacturer = manufacturer;
        this.manufacturingYear = manufacturingYear;
        this.maxCapacity = maxCapacity;
        this.status = status;
        this.totalFlightHours = totalFlightHours;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getManufacturer() { return manufacturer; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public Integer getManufacturingYear() { return manufacturingYear; }
    public void setManufacturingYear(Integer manufacturingYear) { this.manufacturingYear = manufacturingYear; }

    public Integer getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(Integer maxCapacity) { this.maxCapacity = maxCapacity; }

    public AircraftStatus getStatus() { return status; }
    public void setStatus(AircraftStatus status) { this.status = status; }

    public Double getTotalFlightHours() { return totalFlightHours; }
    public void setTotalFlightHours(Double totalFlightHours) { this.totalFlightHours = totalFlightHours; }

    public LocalDateTime getLastMaintenanceDate() { return lastMaintenanceDate; }
    public void setLastMaintenanceDate(LocalDateTime lastMaintenanceDate) { this.lastMaintenanceDate = lastMaintenanceDate; }

    public LocalDateTime getNextMaintenanceDate() { return nextMaintenanceDate; }
    public void setNextMaintenanceDate(LocalDateTime nextMaintenanceDate) { this.nextMaintenanceDate = nextMaintenanceDate; }

    public List<FlightLog> getFlightLogs() { return flightLogs; }
    public void setFlightLogs(List<FlightLog> flightLogs) { this.flightLogs = flightLogs; }

    public List<MaintenanceRecord> getMaintenanceRecords() { return maintenanceRecords; }
    public void setMaintenanceRecords(List<MaintenanceRecord> maintenanceRecords) { this.maintenanceRecords = maintenanceRecords; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

enum AircraftStatus {
    ACTIVE, MAINTENANCE, RETIRED, GROUNDED
}