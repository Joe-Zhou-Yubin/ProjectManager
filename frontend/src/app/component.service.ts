// component.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ComponentService {
  private baseUrl = 'http://localhost:8080/det'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  getDetailsByComponent(compNumber: string): Observable<any> {
    const url = `${this.baseUrl}/getbycomp/${compNumber}`;
    return this.http.get(url);
  }

  createDetail(detailData: any): Observable<any> {
    const url = `${this.baseUrl}/create/${detailData.compNumber}`;
    return this.http.post(url, detailData);
  }

  deleteDetail(detNumber: string): Observable<any> {
    const url = `${this.baseUrl}/delete/${detNumber}`;
    return this.http.delete(url);
}

completeDetail(detNumber: string): Observable<any> {
  const url = `${this.baseUrl}/complete/${detNumber}`;
  return this.http.put(url, {});
}

incompleteDetail(detNumber: string): Observable<any> {
  const url = `${this.baseUrl}/incomplete/${detNumber}`;
  return this.http.put(url, {});
}



}
