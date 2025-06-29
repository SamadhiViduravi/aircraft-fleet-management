package com.ifs.fleetmanagement.service;

import com.ifs.fleetmanagement.dto.AircraftDTO;
import com.ifs.fleetmanagement.dto.AircraftStatsDTO;
import com.ifs.fleetmanagement.exception.ResourceNotFoundException;
import com.ifs.fleetmanagement.model.Aircraft;
import com.ifs.fleetmanagement.model.AircraftStatus;
import com.ifs.fleetmanagement.repository.AircraftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    public List<AircraftDTO> getAllAircraft() {
        return aircraftRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Page<AircraftDTO> getAllAircraftPaginated(Pageable pageable) {
        return aircraftRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public AircraftDTO getAircraftById(Long id) {
        Aircraft aircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id));
        return convertToDTO(aircraft);
    }

    public AircraftDTO getAircraftByRegistrationNumber(String registrationNumber) {
        Aircraft aircraft = aircraftRepository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with registration: " + registrationNumber));
        return convertToDTO(aircraft);
    }

    public AircraftDTO createAircraft(AircraftDTO aircraftDTO) {
        // Check if registration number already exists
        if (aircraftRepository.findByRegistrationNumber(aircraftDTO.getRegistrationNumber()).isPresent()) {
            throw new IllegalArgumentException("Aircraft with registration number " + aircraftDTO.getRegistrationNumber() + " already exists");
        }

        Aircraft aircraft = convertToEntity(aircraftDTO);
        Aircraft savedAircraft = aircraftRepository.save(aircraft);
        return convertToDTO(savedAircraft);
    }

    public AircraftDTO updateAircraft(Long id, AircraftDTO aircraftDTO) {
        Aircraft existingAircraft = aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft not found with id: " + id));

        // Update fields
        existingAircraft.setModel(aircraftDTO.getModel());
        existingAircraft.setManufacturer(aircraftDTO.getManufacturer());
        existingAircraft.setManufacturingYear(aircraftDTO.getManufacturingYear());
        existingAircraft.setMaxCapacity(aircraftDTO.getMaxCapacity());
        existingAircraft.setStatus(AircraftStatus.valueOf(aircraftDTO.getStatus()));
        existingAircraft.setTotalFlightHours(aircraftDTO.getTotalFlightHours());
        existingAircraft.setLastMaintenanceDate(aircraftDTO.getLastMaintenanceDate());
        existingAircraft.setNextMaintenanceDate(aircraftDTO.getNextMaintenanceDate());

        Aircraft updatedAircraft = aircraftRepository.save(existingAircraft);
        return convertToDTO(updatedAircraft);
    }

    public void deleteAircraft(Long id) {
        if (!aircraftRepository.existsById(id)) {
            throw new ResourceNotFoundException("Aircraft not found with id: " + id);
        }
        aircraftRepository.deleteById(id);
    }

    public List<AircraftDTO> getAircraftByStatus(AircraftStatus status) {
        return aircraftRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AircraftDTO> getAircraftDueForMaintenance() {
        LocalDateTime now = LocalDateTime.now();
        return aircraftRepository.findAircraftDueForMaintenance(now).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public AircraftStatsDTO getAircraftStatistics() {
        Long totalAircraft = aircraftRepository.count();
        Long activeAircraft = aircraftRepository.countByStatus(AircraftStatus.ACTIVE);
        Long maintenanceAircraft = aircraftRepository.countByStatus(AircraftStatus.MAINTENANCE);
        Long retiredAircraft = aircraftRepository.countByStatus(AircraftStatus.RETIRED);

        return new AircraftStatsDTO(totalAircraft, activeAircraft, maintenanceAircraft, retiredAircraft);
    }

    public Page<AircraftDTO> searchAircraft(String searchTerm, Pageable pageable) {
        return aircraftRepository.searchAircraft(searchTerm, searchTerm, pageable)
                .map(this::convertToDTO);
    }

    private AircraftDTO convertToDTO(Aircraft aircraft) {
        AircraftDTO dto = new AircraftDTO();
        dto.setId(aircraft.getId());
        dto.setRegistrationNumber(aircraft.getRegistrationNumber());
        dto.setModel(aircraft.getModel());
        dto.setManufacturer(aircraft.getManufacturer());
        dto.setManufacturingYear(aircraft.getManufacturingYear());
        dto.setMaxCapacity(aircraft.getMaxCapacity());
        dto.setStatus(aircraft.getStatus().name());
        dto.setTotalFlightHours(aircraft.getTotalFlightHours());
        dto.setLastMaintenanceDate(aircraft.getLastMaintenanceDate());
        dto.setNextMaintenanceDate(aircraft.getNextMaintenanceDate());
        dto.setCreatedAt(aircraft.getCreatedAt());
        dto.setUpdatedAt(aircraft.getUpdatedAt());
        return dto;
    }

    private Aircraft convertToEntity(AircraftDTO dto) {
        Aircraft aircraft = new Aircraft();
        aircraft.setRegistrationNumber(dto.getRegistrationNumber());
        aircraft.setModel(dto.getModel());
        aircraft.setManufacturer(dto.getManufacturer());
        aircraft.setManufacturingYear(dto.getManufacturingYear());
        aircraft.setMaxCapacity(dto.getMaxCapacity());
        aircraft.setStatus(AircraftStatus.valueOf(dto.getStatus()));
        aircraft.setTotalFlightHours(dto.getTotalFlightHours());
        aircraft.setLastMaintenanceDate(dto.getLastMaintenanceDate());
        aircraft.setNextMaintenanceDate(dto.getNextMaintenanceDate());
        return aircraft;
    }
}