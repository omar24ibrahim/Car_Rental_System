import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import {
    AbstractControl,
    FormControl,
    FormGroup,
    NonNullableFormBuilder,
    ReactiveFormsModule,
    ValidatorFn,
    Validators
} from '@angular/forms';
import { NzFormModule } from 'ng-zorro-antd/form'
import { NzButtonModule } from 'ng-zorro-antd/button'
import { NzInputModule } from 'ng-zorro-antd/input'
import { NzModalService } from 'ng-zorro-antd/modal';
import { OfficeModel } from "../office.model";
import { OfficeService } from "../office.service";


@Component({
    selector: 'app-officeCreate',
    templateUrl: './office-create.component.html',
    styleUrl: './office-create.component.css',
    standalone: true,
    imports: [CommonModule, NzFormModule, NzButtonModule,
        ReactiveFormsModule, NzInputModule],
    providers: [ NzModalService, OfficeService]
})
export class OfficeCreateComponent {
    validateForm: FormGroup<{
        country: FormControl<string>;
        city: FormControl<string>;
        branch: FormControl<string>;
        email: FormControl<string>;
        password: FormControl<string>;
        confirm: FormControl<string>;
    }>;

    constructor(private fb: NonNullableFormBuilder, private modal: NzModalService,
         private officeService: OfficeService) {
        this.validateForm = this.fb.group({
            country: ['', [Validators.required]],
            city: ['', [Validators.required]],
            branch: ['', [Validators.required]],
            email: ['', [Validators.email, Validators.required]],
            password: ['', [Validators.required]],
            confirm: ['', [this.confirmValidator]]
        });
    }

    ngOnInit(): void { }

    submitForm(): void {
        const officeModel: OfficeModel = {
            country: this.validateForm.value.country,
            city: this.validateForm.value.city,
            branch: this.validateForm.value.branch,
            email: this.validateForm.value.email,
            password: this.validateForm.value.password
        };
        this.officeService.addOffice(officeModel).pipe().subscribe({
            next: (response) => this.success(),
            error: (error) => this.error(error.error)
        });
    }

    resetForm(e: MouseEvent): void {
        e.preventDefault();
        this.validateForm.reset();
    }

    validateConfirmPassword(): void {
        setTimeout(() => this.validateForm.controls.confirm.updateValueAndValidity());
    }

    confirmValidator: ValidatorFn = (control: AbstractControl) => {
        if (!control.value) {
            return { error: true, required: true };
        } else if (control.value !== this.validateForm.controls.password.value) {
            return { confirm: true, error: true };
        }
        return {};
    };

    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }

    private success(): void {
        this.modal.success({
            nzTitle: 'Success',
            nzContent: 'Office Created Successfuly',
            nzOnOk: ()=> window.location.reload()
        });
    }
}