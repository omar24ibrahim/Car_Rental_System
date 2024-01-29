import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import {
    FormControl,
    FormGroup,
    NonNullableFormBuilder,
    ReactiveFormsModule,
    Validators
} from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form'
import { NzButtonModule } from 'ng-zorro-antd/button'
import { NzDropDownModule } from 'ng-zorro-antd/dropdown'
import { NzInputModule } from 'ng-zorro-antd/input'
import { NzModalService } from 'ng-zorro-antd/modal';
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker';
import { FormsModule } from '@angular/forms';
import { NzPageHeaderModule } from "ng-zorro-antd/page-header";
import { Reservation } from "./car-reserve.model";
import { UserService } from "../../main/user/user.service";
import { UserModel } from "../../main/user/user.model";
import { Car } from "../car.model";
import { NzMessageService } from "ng-zorro-antd/message";
import { NzCardComponent, NzCardMetaComponent } from 'ng-zorro-antd/card'
import { NzAvatarComponent } from 'ng-zorro-antd/avatar'
import { NzDescriptionsModule } from "ng-zorro-antd/descriptions";
import { CarReservationService } from "./car-reserve.service";
import { Router } from "@angular/router";

@Component({
    selector: 'app-carReserve',
    templateUrl: './car-reserve.component.html',
    styleUrls: ['./car-reserve.component.css'],
    standalone: true,
    imports: [CommonModule, NzFormModule, NzButtonModule,
        ReactiveFormsModule, NzInputModule, NzDropDownModule,
        NzDatePickerModule, FormsModule, NzPageHeaderModule,
        NzCardComponent, NzCardMetaComponent, NzDescriptionsModule,
         NzAvatarComponent],
    providers: [NzModalService, UserService, CarReservationService, NzModalService]
})
export class CarReservationComponent {

    title: string;
    subtitle: string;
    startDate: Date;
    endDate: Date;
    reservation: Reservation;
    user: UserModel | null;
    car: Car;
    description: string = "";

    validateForm: FormGroup<{
       // method: FormControl<string>;
    }>;

    constructor(private fb: NonNullableFormBuilder, private userService: UserService, 
        private modal: NzModalService, private message: NzMessageService, private router: Router,
         private reservationService: CarReservationService) {
        this.title = 'Car Rental Reservation'
        this.subtitle = 'Drive with dreams? Hire from here!'
        this.validateForm = this.fb.group({
        });
    }

    onChange(result: Date): void {
        console.log('onChange: ', result);
    }

    ngOnInit(): void {
        if (this.userService.getUser != null) {
            this.user = this.userService.getUser();
        }

        if (typeof history !== 'undefined') {
            this.car = history.state.car;
            this.description = this.car.rate + "$ per day";
        }
    }


    resetForm(e: MouseEvent): void {
        e.preventDefault();
        this.validateForm.reset();
    }

    submitForm(): void {
        const today = new Date();
        today.setHours(0, 0, 0, 0);
        if (this.startDate && this.endDate) {
            if ((this.startDate > this.endDate) || (this.compareDates(this.startDate, today) < 0)) {
                this.createBasicMessage('invalid dates');
            }
            else {
                this.reservation = {
                    startDate: this.startDate,
                    endDate: this.endDate,
                    user: { id: this.user?.id },
                    car: { plateId: this.car.plateId }
                }
                //calculate fees if confirmed reserve
                //if payement cash 
                this.reservationService.reserveCar(this.reservation).pipe().subscribe({
                    next: (response) => this.success(),
                    error: (error) => this.error(error.error)
                });
            }
        }
        else {
            this.createBasicMessage('You should enter dates');
        }
    }

    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }

    private success(): void {
        this.modal.success({
            nzTitle: 'Success',
            nzContent: 'Car reserved Successfuly',
            nzOnOk: () =>  this.router.navigateByUrl('car-rental')
        });
    }

    createBasicMessage(message: string):void{
        this.message.info(message);
    }

    private compareDates(date1: Date, date2: Date): number {
        // Compare based on day, month, and year
        if (date1.getFullYear() !== date2.getFullYear()) {
          return date1.getFullYear() - date2.getFullYear();
        }
        if (date1.getMonth() !== date2.getMonth()) {
          return date1.getMonth() - date2.getMonth();
        }
        return date1.getDate() - date2.getDate();
      }


}