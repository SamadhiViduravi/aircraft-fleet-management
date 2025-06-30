import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Aircraft, AircraftStatus } from '../../models/aircraft.model';
import { AircraftService, PageResponse } from '../../services/aircraft.service';

@Component({
  selector: 'app-aircraft-list',
  templateUrl: './aircraft-list.component.html',
  styleUrls: ['./aircraft-list.component.css']
})
export class AircraftListComponent implements OnInit {
  aircraft: Aircraft[] = [];
  loading = false;
  error: string | null = null;
  
  // Pagination
  currentPage = 0;
  pageSize = 10;
  totalElements = 0;
  totalPages = 0;
  
  // Sorting
  sortBy = 'id';
  sortDir = 'asc';
  
  // Search
  searchQuery = '';
  
  // Filter
  selectedStatus: AircraftStatus | '' = '';
  
  // Status options for dropdown
  statusOptions = Object.values(AircraftStatus);

  constructor(
    private aircraftService: AircraftService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loadAircraft();
  }

  loadAircraft(): void {
    this.loading = true;
    this.error = null;

    if (this.searchQuery.trim()) {
      this.searchAircraft();
    } else if (this.selectedStatus) {
      this.loadAircraftByStatus();
    } else {
      this.loadAllAircraft();
    }
  }

  private loadAllAircraft(): void {
    this.aircraftService.getAllAircraft(this.currentPage, this.pageSize, this.sortBy, this.sortDir)
      .subscribe({
        next: (response: PageResponse<Aircraft>) => {
          this.aircraft = response.content;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load aircraft data';
          this.loading = false;
          console.error('Error loading aircraft:', error);
        }
      });
  }

  private loadAircraftByStatus(): void {
    this.aircraftService.getAircraftByStatus(this.selectedStatus as AircraftStatus)
      .subscribe({
        next: (aircraft: Aircraft[]) => {
          this.aircraft = aircraft;
          this.totalElements = aircraft.length;
          this.totalPages = 1;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load aircraft data';
          this.loading = false;
          console.error('Error loading aircraft by status:', error);
        }
      });
  }

  private searchAircraft(): void {
    this.aircraftService.searchAircraft(this.searchQuery, this.currentPage, this.pageSize)
      .subscribe({
        next: (response: PageResponse<Aircraft>) => {
          this.aircraft = response.content;
          this.totalElements = response.totalElements;
          this.totalPages = response.totalPages;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to search aircraft';
          this.loading = false;
          console.error('Error searching aircraft:', error);
        }
      });
  }

  onSearch(): void {
    this.currentPage = 0;
    this.selectedStatus = '';
    this.loadAircraft();
  }

  onStatusFilter(): void {
    this.currentPage = 0;
    this.searchQuery = '';
    this.loadAircraft();
  }

  onSort(column: string): void {
    if (this.sortBy === column) {
      this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortBy = column;
      this.sortDir = 'asc';
    }
    this.currentPage = 0;
    this.loadAircraft();
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.loadAircraft();
  }

  onPageSizeChange(): void {
    this.currentPage = 0;
    this.loadAircraft();
  }

  viewAircraft(id: number): void {
    this.router.navigate(['/aircraft', id]);
  }

  editAircraft(id: number): void {
    this.router.navigate(['/aircraft', id, 'edit']);
  }

  deleteAircraft(aircraft: Aircraft): void {
    if (confirm(`Are you sure you want to delete aircraft ${aircraft.registrationNumber}?`)) {
      this.aircraftService.deleteAircraft(aircraft.id!).subscribe({
        next: () => {
          this.loadAircraft();
        },
        error: (error) => {
          this.error = 'Failed to delete aircraft';
          console.error('Error deleting aircraft:', error);
        }
      });
    }
  }

  addNewAircraft(): void {
    this.router.navigate(['/aircraft/new']);
  }

  getStatusBadgeClass(status: AircraftStatus): string {
    switch (status) {
      case AircraftStatus.ACTIVE:
        return 'badge-success';
      case AircraftStatus.MAINTENANCE:
        return 'badge-warning';
      case AircraftStatus.RETIRED:
        return 'badge-secondary';
      case AircraftStatus.GROUNDED:
        return 'badge-danger';
      default:
        return 'badge-secondary';
    }
  }

  clearFilters(): void {
    this.searchQuery = '';
    this.selectedStatus = '';
    this.currentPage = 0;
    this.loadAircraft();
  }
}