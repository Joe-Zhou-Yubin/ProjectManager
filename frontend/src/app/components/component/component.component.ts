import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ComponentService } from '../../component.service';

@Component({
    selector: 'app-component',
    templateUrl: './component.component.html',
    styleUrls: ['./component.component.css']
})
export class ComponentComponent implements OnInit {
    compNumber: string = '';
    componentDetails: any[] = [];
    showCreateForm: boolean = false;
    newDetailTitle: string = '';
    newDetailDetail: string = '';
    newDetailPriority: string = 'low';
    newDetailDeadline: string = '';
    newDetailUsername: string = '';

    constructor(
        private route: ActivatedRoute,
        private componentService: ComponentService
    ) {}

    ngOnInit(): void {
      this.route.paramMap.subscribe(params => {
        this.compNumber = params.get('compNumber') ?? '';
        this.loadComponentDetails();
      });
    }
    

    fetchComponentDetails(): void {
        this.componentService.getDetailsByComponent(this.compNumber).subscribe(
            (data) => {
                this.componentDetails = data;
            },
            (error) => {
                console.error(error);
            }
        );
    }

    loadComponentDetails(): void {
      this.componentService.getDetailsByComponent(this.compNumber).subscribe(
        (data) => {
          this.componentDetails = data;
        },
        (error) => {
          console.error(error);
        }
      );
    }

    createDetail(): void {
    const detailData = {
      compNumber: this.compNumber,
      detTitle: this.newDetailTitle,
      detDetail: this.newDetailDetail,
      priority: this.newDetailPriority,
      deadline: this.newDetailDeadline,
      username: this.newDetailUsername
    };
    console.log('Creating detail with data:', detailData);
    this.componentService.createDetail(detailData).subscribe(
      (response) => {
        console.log('Detail created successfully', response);
        // Refresh the component details list after creating the detail
        this.loadComponentDetails();
        // Clear the input fields
        this.resetDetailForm();
        console.log('Newly created detail:', response);
      },
      (error) => {
        console.error('Error creating detail', error);
        window.location.reload();
      }
    );
  }

  resetDetailForm(): void {
    this.newDetailTitle = '';
    this.newDetailDetail = '';
    this.newDetailPriority = 'High';
    this.newDetailDeadline = '';
    this.newDetailUsername = '';
  }

  cancelCreate(): void {
    this.showCreateForm = false;
    this.resetDetailForm();
  }

  deleteDetail(detNumber: string): void {
    const confirmDelete = confirm('Are you sure you want to delete this detail?');
    if (confirmDelete) {
        this.componentService.deleteDetail(detNumber).subscribe(
            (response) => {
                console.log('Detail deleted successfully', response);
                // Refresh the component details list after deleting the detail
                this.loadComponentDetails();
                window.location.reload();
            },
            (error) => {
                console.error('Error deleting detail', error);
                window.location.reload();
            }
        );
    }
}

completeDetail(detNumber: string): void {
  this.componentService.completeDetail(detNumber).subscribe(
      () => {
          console.log('Detail status updated to Complete');
          this.loadComponentDetails(); 
          window.location.reload()
      },
      (error) => {
          console.error('Error updating detail status', error);
          window.location.reload()
      }
  );
}

incompleteDetail(detNumber: string): void {
  this.componentService.incompleteDetail(detNumber).subscribe(
      () => {
          console.log('Detail status updated to Incomplete');
          this.loadComponentDetails(); 
          window.location.reload()
      },
      (error) => {
          console.error('Error updating detail status', error);
          window.location.reload()
      }
  );
}




}