import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProjectService } from '../../project.service'; // Import your project service
import { Router } from '@angular/router';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {
  projNumber: string = ''; // Initialize the property here
  components: any[] = [];
  showCreateComponentForm: boolean = false;
  newCompTitle: string = '';
  newCompDetail: string = '';

  constructor(private route: ActivatedRoute, private projectService: ProjectService, private router: Router) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.projNumber = params.get('projNumber')!; // Use non-null assertion operator
      this.getComponentsForProject(this.projNumber);
    });
  }
  

  getComponentsForProject(projNumber: string): void {
    this.projectService.getComponents(projNumber).subscribe(
      (data) => {
        this.components = data;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  deleteComponent(compNumber: string): void {
    const confirmDelete = confirm('Are you sure you want to delete this component?');
    if (confirmDelete) {
      this.projectService.deleteComponent(compNumber).subscribe(
        (response) => {
          console.log('Component deleted successfully', response);

          // Update the components array to remove the deleted component
          this.components = this.components.filter(component => component.compNumber !== compNumber);
          this.router.navigate([`/project/${this.projNumber}`]);
        },
        (error) => {
          console.error('Error deleting component', error);
          this.router.navigate([`/project/${this.projNumber}`]);
        }
      );
    }
  }

  toggleCreateComponentForm(): void {
    this.showCreateComponentForm = !this.showCreateComponentForm;
    this.newCompTitle = '';
    this.newCompDetail = '';
  }

  createComponent(): void {
    const componentData = {
      compTitle: this.newCompTitle,
      compDetail: this.newCompDetail
    };

    this.projectService.createComponentForProject(this.projNumber, componentData).subscribe(
      (response) => {
        console.log('Component created successfully', response);
        this.toggleCreateComponentForm();
        window.location.reload();
      },
      (error) => {
        console.error('Error creating component', error);
        window.location.reload();
      }
    );
  }


}
