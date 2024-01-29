import { Component, Input } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router, RouterOutlet } from "@angular/router";
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzSpaceComponent, NzSpaceItemDirective } from 'ng-zorro-antd/space';
import { NzDescriptionsItemComponent } from 'ng-zorro-antd/descriptions';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { CarService } from "../car/car.service";
import { Car } from "../car/car.model";
import { CarComponent } from "../car/car.component";
import { UserService } from "../main/user/user.service";
import { UserModel } from "../main/user/user.model";
import { Visibility } from "../models/roles.model";
import { NzDropDownModule } from "ng-zorro-antd/dropdown";
import {
  FormControl,
  FormGroup,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';
import { NzIconModule } from 'ng-zorro-antd/icon';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NzGridModule, CarComponent, NzPageHeaderModule, ReactiveFormsModule,
    NzDescriptionsItemComponent, NzSpaceComponent, NzButtonComponent, NzSpaceItemDirective, NzDropDownModule, NzIconModule],
  providers: [CarService, UserService]
})
export class AdminComponent {

  carRows: Car[][] = [];
  user: UserModel | null;
  subtitle: string;
  visibility: Visibility | undefined;
  title: string;
  attributes: string[] = ['brand', 'color', 'type', 'body', 'fuel', 'status', 'transmission', 'capacity', 'rate', 'year', 'plate'];
  attributeValue: string;
  attributeName: string = '';
  search: boolean = false;
  reset: boolean = false;
  searchForm: FormGroup<{attributeValue: FormControl<string>;}>;
  reload: boolean = false;

  constructor(private fb: NonNullableFormBuilder,private carService: CarService, private userService: UserService
    ,private router: Router) {
      this.visibility = this.userService.getVisibility();
      this.title = 'Car Rental ' + this.visibility?.getType().toLowerCase();
      this.searchForm = this.fb.group({attributeValue: ['', [Validators.required]]});
    }

  ngOnInit(): void {
    this.user = this.userService.getUser();
    if(this.user != null) {
      this.subtitle = this.user.firstName + " " + this.user.lastName;
      this.reload = true;
    }
    if (this.visibility?.getType() === 'CLIENT'){
      this.getAllActiveCars();
    } else  if (this.visibility?.getType() === 'OFFICE') {
      this.getAllOfficeCars(String(this.user?.email));
    } else {
      this.getAllCars();
    }
    this.reload = false;
  }

  private getAllOfficeCars(officeEmail: string) {
    this.carService.getOfficeCars(officeEmail).pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private getAllActiveCars() {
    this.carService.getActiveCars().pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private getAllCars() {
    this.carService.getCars().pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private getAllOfficeCarsByAttribute(officeEmail: string, attributeName: string, attributeValue:string) {
    this.carService.getOfficeCarsByAttribute(officeEmail, attributeName, attributeValue).pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private getAllActiveCarsByAttribute(attributeName: string, attributeValue: string) {
    this.carService.getActiveCarsByAttribute(attributeName, attributeValue).pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private getAllCarsByAttribute(attributeName: string, attributeValue: string) {
    this.carService.getCarsByAttribute(attributeName, attributeValue).pipe().subscribe({
      next: (response: Car[] | null) => {
        if (response) {
          if (!this.reload) {
            this.carRows = [];
          }
          this.buildCarRows(response);
        } else {
          // Handle the case when the response is null or undefined
          console.log("Cars response is null or undefined");
        }
      },
      error: (error) => console.log(error.error)
    });
  }

  private buildCarRows(response: Car[]) {
    for (let i = 0; i < response.length; i++) {
      const temp = [];
      const start = i;
      while (i < response.length && (i == start || i % 4 != 0)) {
        temp.push(response[i]);
        i++;
      }
      this.carRows.push(temp);
      if (temp.length == 4) i--;
    };
  }

  addCar(): void{
    this.router.navigateByUrl('addCar');
  }

  addOffice(): void{ 
    // TODO ADD OFFICE 
    this.router.navigateByUrl('addOffice');
  }

  findUsers(): void{
    this.router.navigateByUrl('findUser');
  }

  findCars(): void{
    if (this.visibility?.getType() === 'CLIENT') {
      this.getAllActiveCarsByAttribute(this.attributeName, String(this.searchForm.value.attributeValue));
    } else if (this.visibility?.getType() === 'OFFICE') {
      this.getAllOfficeCarsByAttribute(String(this.user?.email), this.attributeName, String(this.searchForm.value.attributeValue));
    } else {
      this.getAllCarsByAttribute(this.attributeName, String(this.searchForm.value.attributeValue));
    }
  }

  showSearchBar(attribute: string): void{
    this.attributeName = attribute;
    this.search = true;
    this.reset = true;
  }

  resetSearch(): void{
    this.searchForm.reset();
    this.search = false;
    this.reset = false;
    this.attributeName = '';
    if (this.visibility?.getType() === 'OFFICE') {
      this.getAllOfficeCars(String(this.user?.email));
    } else if (this.visibility?.getType() === 'CLIENT'){
      this.getAllActiveCars();
    } else {
      this.getAllCars();
    }
  }

  showStats() {
    this.router.navigateByUrl('reservation-stats');
  }

  showPayments() {
    this.router.navigateByUrl('payments');
  }

  signOut() {
    this.userService.clearUser();
    this.router.navigateByUrl('');
  }

  showLogs() {
    this.router.navigateByUrl('logs');
  }
}