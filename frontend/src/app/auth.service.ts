import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import jwtDecode from 'jwt-decode';
import { tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = 'http://localhost:8080'; // Replace with your backend URL

  constructor(private http: HttpClient) {}

  setSession(response: any): void {
    const { accessToken, username, roles } = response; // Destructure the properties
    localStorage.setItem('accessToken', accessToken); // Store the token in local storage
    localStorage.setItem('username', username); // Store the username
    localStorage.setItem('roles', JSON.stringify(roles)); // Store the roles
  }

  login(username: string, password: string): Observable<any> {
    const loginData = {
      username: username,
      password: password,
    };
  
    return this.http.post(`${this.baseUrl}/api/auth/signin`, loginData).pipe(
      tap((response: any) => {
        if (response && response.accessToken) {
          this.setSession(response);
        } else {
          // Handle error case when the access token is not found in the response
        }
      })
    );
  }
  

  public getUserRoles(): string[] {
    const rolesJson = localStorage.getItem('roles');
    if (rolesJson) {
      const userRoles = JSON.parse(rolesJson);
      return userRoles;
    }
    return [];
  }
  
  hasRoles(requiredRoles: string[]): boolean {
    const userRoles = this.getUserRoles();
    return requiredRoles.some(role => userRoles.includes(role));
  }
  
  

  saveToken(token: string): void {
    localStorage.setItem('accessToken', token);
  }

  getToken(): string | null {
    return localStorage.getItem('accessToken');
  }

  public clearSession(): void {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('username');
    localStorage.removeItem('roles');
  }
  

  // You can add more methods here, such as logout, token management, etc.
}
