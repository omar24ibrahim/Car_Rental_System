import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { NzDescriptionsModule } from "ng-zorro-antd/descriptions";
import { NzBadgeModule } from 'ng-zorro-antd/badge'
import { NzPopoverModule } from 'ng-zorro-antd/popover';
import { FormsModule } from '@angular/forms';
import { Car } from "../car.model";
import { NzMessageService } from 'ng-zorro-antd/message';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { CarService } from "../car.service";
import { NzModalService } from "ng-zorro-antd/modal";
import { NzDropDownModule } from "ng-zorro-antd/dropdown";
import { OfficeService } from "../../office/office.service";
import { off } from "process";
import { Visibility } from "../../models/roles.model";
import { UserService } from "../../main/user/user.service";


@Component({
    selector: 'app-carEdit',
    templateUrl: './car-edit.component.html',
    styleUrls: ['./car-edit.component.css'],
    standalone: true,
    imports: [CommonModule, NzDescriptionsModule, NzBadgeModule, NzPopoverModule,
        FormsModule, NzPopconfirmModule, NzDropDownModule],
    providers: [CarService, NzModalService, OfficeService, UserService]
})
export class CarEditComponent {

    car: Car;
    years: string[] = [];
    rateInput: HTMLInputElement;
    officeIds: string[] = [];
    visibility: Visibility | undefined;

    constructor(private nzMessageService: NzMessageService, private carService: CarService
        , private modal: NzModalService, private officeService: OfficeService,
         private userService: UserService) {
            this.visibility = this.userService.getVisibility();
    }

    ngOnInit(): void {
        if (typeof history !== 'undefined') {
            this.car = history.state.car;
        }
        this.generateYearDropdown();
        this.officeService.getAllOfficeIds().subscribe(
            (ids) => {
                this.officeIds = ids.map(String);
            },
            (error) => {
                console.error('Error fetching office IDs', error);
            }
        );
    }

    isEditing: { [key: string]: boolean } = {};

    toggleEdit(field: string): void {
        this.isEditing[field] = !this.isEditing[field];
    }

    saveField(field: string): void {

        if (this.isFieldEmpty(field)) {
            this.nzMessageService.error(`${this.capitalize(field)} cannot be empty.`);
            return;
        }

        if (field === 'rate') {
            // Check if the value is a valid number and not negative
            const rateValue = Number(this.car.rate);
            if (isNaN(rateValue) || rateValue < 0) {
                this.nzMessageService.error('Please enter a valid non-negative number for the rate.');
                return; // Stop execution if the input is invalid
            }
        }
        if (field === 'capacity') {
            // Check if the value is a valid number and not negative
            const capacityValue = Number(this.car.capacity);
            if (isNaN(capacityValue) || capacityValue <= 0) {
                this.nzMessageService.error('Please enter a valid positive number for the capacity.');
                return; // Stop execution if the input is invalid
            }
        }

        this.toggleEdit(field);
    }


    cancel(): void {
        this.nzMessageService.info('cancel');
    }

    confirmUpdate(): void {
        this.carService.updateCar(this.car).pipe().subscribe({
            next: (response) => this.nzMessageService.info('updated'),
            error: (error) => this.error(error.error)
        });
    }


    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }

    setStatus(status: string): void {
        this.car.status = status;
    }

    generateYearDropdown(): void {
        const currentYear = new Date().getFullYear();
        const startYear = 1990; // Adjust as needed
        this.years = Array.from({ length: currentYear - startYear + 1 },
            (_, index) => (startYear + index).toString()).reverse();
    }

    selectYear(year: string): void {
        this.car.year = Number(year);
    }

    setTransmissionType(type: string): void {
        this.car.transmissionType = type;
    }

    setFuelType(type: string): void {
        this.car.fuelType = type;
    }

    setBodyStyle(style: string): void {
        this.car.bodyStyle = style;
    }

    setOffice(office: string): void {
        this.car.office = {officeId: Number(office)};
    }

    private isFieldEmpty(field: string): boolean {
        const fieldValue = this.car[field];

        // Check if the field value is undefined, null, or an empty string
        return fieldValue === undefined || fieldValue === null || fieldValue === '';
    }

    private capitalize(value: string): string {
        // Capitalize the first letter of the field name
        return value.charAt(0).toUpperCase() + value.slice(1);
    }
}