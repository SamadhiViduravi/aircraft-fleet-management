import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Aircraft, AircraftStats, AircraftStatus } from '../models/aircraft.model';

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class AircraftService {
  private apiUrl = 'http://localhost:8080/api/aircraft';

  constructor(private http: HttpClient) {}

  getAllAircraft(page: number = 0, size: number = 10, sortBy: string = 'id', sortDir: string = 'asc'): Observable<PageResponse<Aircraft>> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString())
      .set('sortBy', sortBy)
      .set('sortDir', sortDir);

    return this.http.get<PageResponse<Aircraft>>(this.apiUrl, { params });
  }

  getAircraftById(id: number): Observable<Aircraft> {
    return this.http.get<Aircraft>(`${this.apiUrl}/${id}`);
  }

  getAircraftByRegistration(registrationNumber: string): Observable<Aircraft> {
    return this.http.get<Aircraft>(`${this.apiUrl}/registration/${registrationNumber}`);
  }

  createAircraft(aircraft: Aircraft): Observable<Aircraft> {
    return this.http.post<Aircraft>(this.apiUrl, aircraft);
  }

  updateAircraft(id: number, aircraft: Aircraft): Observable<Aircraft> {
    return this.http.put<Aircraft>(`${this.apiUrl}/${id}`, aircraft);
  }

  deleteAircraft(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getAircraftByStatus(status: AircraftStatus): Observable<Aircraft[]> {
    return this.http.get<Aircraft[]>(`${this.apiUrl}/status/${status}`);
  }

  getAircraftDueForMaintenance(): Observable<Aircraft[]> {
    return this.http.get<Aircraft[]>(`${this.apiUrl}/maintenance/due`);
  }

  getAircraftStatistics(): Observable<AircraftStats> {
    return this.http.get<AircraftStats>(`${this.apiUrl}/statistics`);
  }

  searchAircraft(query: string, page: number = 0, size: number = 10): Observable<PageResponse<Aircraft>> {
    const params = new HttpParams()
      .set('query', query)
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Aircraft>>(`${this.apiUrl}/search`, { params });
  }
}