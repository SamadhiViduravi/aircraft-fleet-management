package com.ifs.fleetmanagement.controller;

import com.ifs.fleetmanagement.dto.AircraftDTO;
import com.ifs.fleetmanagement.dto.AircraftStatsDTO;
import com.ifs.fleetmanagement.model.AircraftStatus;
import com.ifs.fleetmanagement.service.AircraftService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aircraft")
@CrossOrigin(origins = "http://localhost:4200")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public ResponseEntity<Page<AircraftDTO>> getAllAircraft(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                   Sort.by(sortBy).descending() : 
                   Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<AircraftDTO> aircraft = aircraftService.getAllAircraftPaginated(pageable);
        
        return ResponseEntity.ok(aircraft);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AircraftDTO> getAircraftById(@PathVariable Long id) {
        AircraftDTO aircraft = aircraftService.getAircraftById(id);
        return ResponseEntity.ok(aircraft);
    }

    @GetMapping("/registration/{registrationNumber}")
    public ResponseEntity<AircraftDTO> getAircraftByRegistration(@PathVariable String registrationNumber) {
        AircraftDTO aircraft = aircraftService.getAircraftByRegistrationNumber(registrationNumber);
        return ResponseEntity.ok(aircraft);
    }

    @PostMapping
    public ResponseEntity<AircraftDTO> createAircraft(@Valid @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO createdAircraft = aircraftService.createAircraft(aircraftDTO);
        return new ResponseEntity<>(createdAircraft, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AircraftDTO> updateAircraft(@PathVariable Long id, @Valid @RequestBody AircraftDTO aircraftDTO) {
        AircraftDTO updatedAircraft = aircraftService.updateAircraft(id, aircraftDTO);
        return ResponseEntity.ok(updatedAircraft);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAircraft(@PathVariable Long id) {
        aircraftService.deleteAircraft(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AircraftDTO>> getAircraftByStatus(@PathVariable AircraftStatus status) {
        List<AircraftDTO> aircraft = aircraftService.getAircraftByStatus(status);
        return ResponseEntity.ok(aircraft);
    }

    @GetMapping("/maintenance/due")
    public ResponseEntity<List<AircraftDTO>> getAircraftDueForMaintenance() {
        List<AircraftDTO> aircraft = aircraftService.getAircraftDueForMaintenance();
        return ResponseEntity.ok(aircraft);
    }

    @GetMapping("/statistics")
    public ResponseEntity<AircraftStatsDTO> getAircraftStatistics() {
        AircraftStatsDTO stats = aircraftService.getAircraftStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AircraftDTO>> searchAircraft(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size);
        Page<AircraftDTO> aircraft = aircraftService.searchAircraft(query, pageable);
        
        return ResponseEntity.ok(aircraft);
    }
}