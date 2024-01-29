import { Component, Input, OnInit } from "@angular/core";
import { CarService } from "./car.service";
import { Car } from "./car.model";
import { CommonModule } from "@angular/common";
import { NzCardComponent, NzCardMetaComponent } from 'ng-zorro-antd/card'
import { NzAvatarComponent } from 'ng-zorro-antd/avatar'
import { NzIconModule } from "ng-zorro-antd/icon";
import { NzDescriptionsModule } from "ng-zorro-antd/descriptions";
import { Router } from "@angular/router";
import { UserService } from "../main/user/user.service";
import { Visibility } from "../models/roles.model";

@Component({
  selector: 'app-car',
  templateUrl: './car.component.html',
  styleUrls: ['./car.component.css'],
  standalone: true,
  imports: [CommonModule, NzCardComponent, NzAvatarComponent, NzCardMetaComponent,
    NzIconModule, NzDescriptionsModule],
  providers: [CarService, UserService]
})
export class CarComponent {
  
  @Input({ required: true }) car: Car;
  description: string = "";
  visibility: Visibility | undefined;
  

  constructor(private router: Router, private userService: UserService) { 
    this.visibility = this.userService.getVisibility();
  }

  ngOnInit(): void {
    this.description = this.car.rate + "$ per day";
  }

  showDetails(): void{
    this.router.navigateByUrl('details', {state:{car: this.car}});
  }

  updateDetails(): void{
    this.router.navigateByUrl('editCar', {state:{car: this.car}});
  }

  carReservation(): void{
    this.router.navigateByUrl('reserveCar', {state:{car: this.car}});
  }
}