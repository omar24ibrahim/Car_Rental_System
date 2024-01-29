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
import { CarService } from "../car.service";
import { Car } from "../car.model";
import { OfficeService } from "../../office/office.service";



@Component({
    selector: 'app-carAdd',
    templateUrl: './car-add.component.html',
    styleUrls: ['./car-add.component.css'],
    standalone: true,
    imports: [CommonModule, NzFormModule, NzButtonModule,
        ReactiveFormsModule, NzInputModule, NzDropDownModule],
    providers: [NzModalService, CarService, OfficeService]
})
export class CarAddComponent {

    car: Car;
    years: string[] = [];
    officeIds: string[] = [];

    validateForm: FormGroup<{
        plateId: FormControl<string>;
        brand: FormControl<string>;
        type: FormControl<string>;
        year: FormControl<string>; //calender
        status: FormControl<string>;
        rate: FormControl<string>; //dropdown
        transmissionType: FormControl<string>; //dropdown
        fuelType: FormControl<string>; //dropdown
        bodyStyle: FormControl<string>; //dropdown
        color: FormControl<string>; //color chooser
        capacity: FormControl<string>;
        imageUrl: FormControl<string>; //browse from computer
        officeId: FormControl<string>; //dropdown
    }>;

    constructor(private fb: NonNullableFormBuilder, private modal: NzModalService,
        private carService: CarService, private officeService: OfficeService) {
        this.validateForm = this.fb.group({
            plateId: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
            brand: ['', [Validators.required]],
            type: ['', [Validators.required]],
            year: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
            status: ['', [Validators.required]],
            rate: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
            transmissionType: ['', [Validators.required]],
            fuelType: ['', [Validators.required]],
            bodyStyle: ['', [Validators.required]],
            color: ['', [Validators.required]],
            capacity: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]],
            imageUrl: ['', [Validators.required]],
            officeId: ['', [Validators.required, Validators.pattern(/^[0-9]+$/)]]
        });
    }

    ngOnInit(): void {
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

    submitForm(): void {
        this.car = {
            plateId: Number(this.validateForm.value.plateId),
            brand: this.validateForm.value.brand!,
            type: this.validateForm.value.type!,
            year: Number(this.validateForm.value.year),
            status: this.validateForm.value.status!,
            rate: Number(this.validateForm.value.rate),
            transmissionType: this.validateForm.value.transmissionType!,
            fuelType: this.validateForm.value.fuelType!,
            bodyStyle: this.validateForm.value.bodyStyle!,
            color: this.validateForm.value.color!,
            capacity: Number(this.validateForm.value.capacity),
            imageUrl: this.validateForm.value.imageUrl!,
            office: { officeId: Number(this.validateForm.value.officeId) }
        };
        this.carService.addCar(this.car).pipe().subscribe({
            next: (response) => this.success(),
            error: (error) => this.error(error.error)
        });
    }

    resetForm(e: MouseEvent): void {
        e.preventDefault();
        this.validateForm.reset();
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
            nzContent: 'Car added Successfuly',
            nzOnOk: () => window.location.reload()
        });
    }

    setStatus(data: string): void {
        this.validateForm.get('status')?.setValue(data);
    }

    generateYearDropdown(): void {
        const currentYear = new Date().getFullYear();
        const startYear = 1990; // Adjust as needed
        this.years = Array.from({ length: currentYear - startYear + 1 },
            (_, index) => (startYear + index).toString()).reverse();
    }

    selectYear(year: string): void {
        this.validateForm.get('year')?.setValue(year);
    }

    selectOffice(officeId: string): void{
        this.validateForm.get('officeId')?.setValue(officeId);
    }

    setTransmissionType(transmissionType: string): void{
        this.validateForm.get('transmissionType')?.setValue(transmissionType);
    }

    setFuelType(fuelType: string): void{
        this.validateForm.get('fuelType')?.setValue(fuelType);
    }

    setBodyStyle(bodyStyle: string): void{
        this.validateForm.get('bodyStyle')?.setValue(bodyStyle);
    }
}