package com.ifs.fleetmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "flight_logs")
@EntityListeners(AuditingEntityListener.class)
public class FlightLog {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_log_seq")
    @SequenceGenerator(name = "flight_log_seq", sequenceName = "flight_log_sequence", allocationSize = 1)
    private Long id;

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Flight duration is required")
    private Double flightDurationHours;

    @NotBlank(message = "Pilot name is required")
    private String pilotName;

    @NotBlank(message = "Co-pilot name is required")
    private String coPilotName;

    private Integer passengerCount;
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aircraft_id", nullable = false)
    private Aircraft aircraft;

    @CreatedDate
    private LocalDateTime createdAt;

    // Constructors
    public FlightLog() {}

    public FlightLog(String flightNumber, String origin, String destination, 
                    LocalDateTime departureTime, LocalDateTime arrivalTime, 
                    Double flightDurationHours, String pilotName, String coPilotName, 
                    Integer passengerCount, Aircraft aircraft) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flightDurationHours = flightDurationHours;
        this.pilotName = pilotName;
        this.coPilotName = coPilotName;
        this.passengerCount = passengerCount;
        this.aircraft = aircraft;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public Double getFlightDurationHours() { return flightDurationHours; }
    public void setFlightDurationHours(Double flightDurationHours) { this.flightDurationHours = flightDurationHours; }

    public String getPilotName() { return pilotName; }
    public void setPilotName(String pilotName) { this.pilotName = pilotName; }

    public String getCoPilotName() { return coPilotName; }
    public void setCoPilotName(String coPilotName) { this.coPilotName = coPilotName; }

    public Integer getPassengerCount() { return passengerCount; }
    public void setPassengerCount(Integer passengerCount) { this.passengerCount = passengerCount; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public Aircraft getAircraft() { return aircraft; }
    public void setAircraft(Aircraft aircraft) { this.aircraft = aircraft; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}