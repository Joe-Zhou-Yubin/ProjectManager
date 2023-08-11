import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
  }

  hasLeaderOrManagerRole(): boolean {
    const userRoles = this.authService.getUserRoles();
    return userRoles.includes('ROLE_LEADER') || userRoles.includes('ROLE_MANAGER');
  }

  logout(): void {
    this.authService.clearSession();
    this.router.navigate(['/login']); // Redirect to login page after logout
  }
}
