import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class HomeService {
  private projectsUrl = 'http://localhost:8080/project'; // Update with your API endpoint

  constructor(private http: HttpClient) {}
  

  getProjects(): Observable<any[]> {
    return this.http.get<any[]>(`${this.projectsUrl}/getprojects`);
  }

  createProject(projectData: any): Observable<any> {
    const url = `${this.projectsUrl}/create`;
    return this.http.post(url, projectData);
  }
  deleteProject(projNumber: string): Observable<any> {
    const url = `${this.projectsUrl}/${projNumber}`;
    return this.http.delete(url);
  }
  completeProject(projNumber: string): Observable<any>{
    const url = `${this.projectsUrl}/complete/${projNumber}`;
    return this.http.put(url, {});
  }

  incompleteProject(projNumber: string): Observable<any>{
    const url = `${this.projectsUrl}/incomplete/${projNumber}`;
    return this.http.put(url, {});
  }
  

}
