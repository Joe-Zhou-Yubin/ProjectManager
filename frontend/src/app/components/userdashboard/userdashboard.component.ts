import { Component, OnInit } from '@angular/core';
import { UserService } from '../../user.service'; // Import your user service here

@Component({
  selector: 'app-userdashboard',
  templateUrl: './userdashboard.component.html',
  styleUrls: ['./userdashboard.component.css']
})
export class UserdashboardComponent implements OnInit {
  users: any[] = [];
  showCreateForm: boolean = false;
  newUser: any = {};
  selectedRole: string = '';
  role: string[] = [];
  memberRole: boolean = false;
  managerRole: boolean = false;
  leaderRole: boolean = false;

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.getUsers().subscribe(
      (data) => {
        this.users = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  deleteUser(userId: number): void {
    this.userService.deleteUser(userId).subscribe(
      () => {
        // After successful deletion, reload the user list
        this.loadUsers();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  toggleCreateForm() {
    this.showCreateForm = !this.showCreateForm;
    this.newUser = {}; // Clear new user details when toggling form
  }

  createUser() {
    let role: string[] = [];
    
    if (this.memberRole) {
      role.push("member");
    }
    if (this.managerRole) {
      role.push("manager");
    }
    if (this.leaderRole) {
      role.push("leader");
    }
  
    const newUser = {
      username: this.newUser.username,
      email: this.newUser.email,
      password: this.newUser.password,
      role: role
    };
  
    console.log("New User:", newUser); // Add this console log
  
    this.userService.createUser(newUser).subscribe(
      (response) => {
        // User created successfully, handle the response if needed
        console.log("User created:", response);
        // After successful creation, reload the user list and hide the create form
        this.loadUsers();
        this.showCreateForm = false;
      },
      (error) => {
        // Handle the error if the user creation fails
        console.error("Error creating user:", error);
      }
    );
  }
  

  cancelCreate() {
    this.showCreateForm = false;
    this.newUser = {}; // Clear new user details when canceling
  }
}
