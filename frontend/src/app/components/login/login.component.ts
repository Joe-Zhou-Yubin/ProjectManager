import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from '../../auth.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.username, this.password).subscribe(
      (response) => {
        // Handle successful login response here
        this.authService.saveToken(response.accessToken); // Save token to local storage
        this.router.navigate(['/home']); // Navigate to /home on successful login
      },
      (error) => {
        // Handle login error here
        this.errorMessage = 'Invalid username or password'; // Set error message
        console.error(error);
      }
    );
  }
}
