import { Component, OnInit } from '@angular/core';
import { HomeService } from '../../home.service';
import { Router } from '@angular/router';
import {AuthService} from '../../auth.service'

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  projects: any[] = [];
  showCreateForm: boolean = false;
  newProjectTitle: string = '';
  newProjectDescription: string = '';

  constructor(private homeService: HomeService, private router: Router, private authService: AuthService) {}

  ngOnInit(): void {
    this.getProjects();
  }

  getProjects(): void {
    this.homeService.getProjects().subscribe(
      (data) => {
        this.projects = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  toggleCreateForm(): void {
    this.showCreateForm = !this.showCreateForm;
    this.newProjectTitle = '';
    this.newProjectDescription = '';
  }

  createProject(): void {
    const projectData = {
      projTitle: this.newProjectTitle,
      projDetail: this.newProjectDescription
    };

    this.homeService.createProject(projectData).subscribe(
      (response) => {
        console.log('Project created successfully', response);
        // Refresh the project list after creating the project
        this.getProjects();
        this.toggleCreateForm();
        
      },
      (error) => {
        console.error('Error creating project', error);
        // Log the full response details
        console.error('Full response:', error.error);
        window.location.reload();
      }
    );
  }

  deleteProject(projNumber: string): void {
    const confirmDelete = confirm('Are you sure you want to delete this project?');
    if (confirmDelete) {
      this.homeService.deleteProject(projNumber).subscribe(
        (response) => {
          console.log('Project deleted successfully', response);

          // Update the projects array to remove the deleted project
          this.projects = this.projects.filter(project => project.projNumber !== projNumber);

          this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Error deleting project', error);
          this.router.navigate(['/home']);
        }
      );
    }
  }

  canDeleteProject(): boolean {
    // Check if the user has roles manager or leader
    return this.authService.hasRoles([ 'ROLE_LEADER']);
  }

  
  completeStatus(project: any): boolean {
    return (
      (project.projStatus === false) &&
      this.authService.hasRoles(['ROLE_LEADER'])
    );
  }
  
  incompleteStatus(project: any): boolean {
    return (
      (project.projStatus === true) &&
      this.authService.hasRoles(['ROLE_LEADER'])
    );
  }
  
  
  completeProject(projNumber: string): void {
    this.homeService.completeProject(projNumber).subscribe(
      () => {
        console.log('Project status updated to Complete');
        this.getProjects(); // Refresh the project list after updating the status
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Error updating project status', error);
        this.router.navigate(['/home']);
      }
    );
  }

  incompleteProject(projNumber: string): void {
    this.homeService.incompleteProject(projNumber).subscribe(
      () => {
        console.log('Project status updated to Incomplete');
        this.getProjects(); // Refresh the project list after updating the status
        this.router.navigate(['/home']);
      },
      (error) => {
        console.error('Error updating project status', error);
        this.router.navigate(['/home']);
      }
    );
  }
  
  

}
