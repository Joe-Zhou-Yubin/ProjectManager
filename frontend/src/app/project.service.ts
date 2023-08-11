import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProjectService {
  private baseUrl = 'http://localhost:8080/comp'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getComponents(projNumber: string): Observable<any[]> {
    const url = `${this.baseUrl}/get/${projNumber}`;
    return this.http.get<any[]>(url);
  }

  deleteComponent(compNumber: string): Observable<any> {
    const url = `${this.baseUrl}/delete/${compNumber}`;
    return this.http.delete(url);
  }

  createComponentForProject(projNumber: string, componentData: any): Observable<any> {
    const url = `${this.baseUrl}/create/${projNumber}`;
    return this.http.post(url, componentData);
  }
}
