import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean | UrlTree {
    const token = this.authService.getToken();
    if (token) {
      const requiredRoles = next.data['requiredRoles']; // Access requiredRoles using bracket notation
      if (!requiredRoles || this.authService.hasRoles(requiredRoles)) {
        return true; // Allow navigation if roles match or no required roles are specified
      } else {
        return this.router.parseUrl('/home'); // Redirect to access-denied page if roles don't match
      }
    } else {
      return this.router.parseUrl('/login'); // Redirect to /login if token is not present
    }
  }
}
