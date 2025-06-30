export interface Aircraft {
  id?: number;
  registrationNumber: string;
  model: string;
  manufacturer: string;
  manufacturingYear: number;
  maxCapacity: number;
  status: AircraftStatus;
  totalFlightHours: number;
  lastMaintenanceDate?: Date;
  nextMaintenanceDate?: Date;
  createdAt?: Date;
  updatedAt?: Date;
}

export enum AircraftStatus {
  ACTIVE = 'ACTIVE',
  MAINTENANCE = 'MAINTENANCE',
  RETIRED = 'RETIRED',
  GROUNDED = 'GROUNDED'
}

export interface AircraftStats {
  totalAircraft: number;
  activeAircraft: number;
  maintenanceAircraft: number;
  retiredAircraft: number;
}

export interface FlightLog {
  id?: number;
  flightNumber: string;
  origin: string;
  destination: string;
  departureTime: Date;
  arrivalTime: Date;
  flightDurationHours: number;
  pilotName: string;
  coPilotName: string;
  passengerCount?: number;
  remarks?: string;
  aircraftId: number;
  createdAt?: Date;
}

export interface MaintenanceRecord {
  id?: number;
  maintenanceDate: Date;
  maintenanceType: MaintenanceType;
  description: string;
  technicianName: string;
  cost: number;
  status: MaintenanceStatus;
  remarks?: string;
  aircraftId: number;
  createdAt?: Date;
}

export enum MaintenanceType {
  ROUTINE = 'ROUTINE',
  SCHEDULED = 'SCHEDULED',
  EMERGENCY = 'EMERGENCY',
  INSPECTION = 'INSPECTION',
  REPAIR = 'REPAIR',
  OVERHAUL = 'OVERHAUL'
}

export enum MaintenanceStatus {
  SCHEDULED = 'SCHEDULED',
  IN_PROGRESS = 'IN_PROGRESS',
  COMPLETED = 'COMPLETED',
  CANCELLED = 'CANCELLED'
}