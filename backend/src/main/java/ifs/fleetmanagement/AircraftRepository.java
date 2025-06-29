package com.ifs.fleetmanagement.repository;

import com.ifs.fleetmanagement.model.Aircraft;
import com.ifs.fleetmanagement.model.AircraftStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    
    Optional<Aircraft> findByRegistrationNumber(String registrationNumber);
    
    List<Aircraft> findByStatus(AircraftStatus status);
    
    List<Aircraft> findByManufacturer(String manufacturer);
    
    Page<Aircraft> findByStatusAndManufacturer(AircraftStatus status, String manufacturer, Pageable pageable);
    
    @Query("SELECT a FROM Aircraft a WHERE a.nextMaintenanceDate <= :date")
    List<Aircraft> findAircraftDueForMaintenance(@Param("date") LocalDateTime date);
    
    @Query("SELECT a FROM Aircraft a WHERE a.totalFlightHours > :hours")
    List<Aircraft> findAircraftWithFlightHoursGreaterThan(@Param("hours") Double hours);
    
    @Query("SELECT COUNT(a) FROM Aircraft a WHERE a.status = :status")
    Long countByStatus(@Param("status") AircraftStatus status);
    
    @Query("SELECT a.manufacturer, COUNT(a) FROM Aircraft a GROUP BY a.manufacturer")
    List<Object[]> getAircraftCountByManufacturer();
    
    @Query("SELECT a FROM Aircraft a WHERE a.model LIKE %:model% OR a.registrationNumber LIKE %:registration%")
    Page<Aircraft> searchAircraft(@Param("model") String model, @Param("registration") String registration, Pageable pageable);
}