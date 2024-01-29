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
import { UserService } from "../user/user.service";
import { UserModel } from "../user/user.model";
import { NzModalService } from 'ng-zorro-antd/modal';


@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrl: './register.component.css',
    standalone: true,
    imports: [CommonModule, NzFormModule, NzButtonModule,
        ReactiveFormsModule, NzInputModule],
    providers: [UserService, NzModalService]
})
export class RegisterComponent {
    validateForm: FormGroup<{
        firstName: FormControl<string>;
        lastName: FormControl<string>;
        email: FormControl<string>;
        password: FormControl<string>;
        confirm: FormControl<string>;
    }>;

    constructor(private fb: NonNullableFormBuilder, private userService: UserService,
        private modal: NzModalService) {
        this.validateForm = this.fb.group({
            firstName: ['', [Validators.required]],
            lastName: ['', [Validators.required]],
            email: ['', [Validators.email, Validators.required]],
            password: ['', [Validators.required]],
            confirm: ['', [this.confirmValidator]]
        });
    }

    ngOnInit(): void { }

    submitForm(): void {
        const userModel: UserModel = {
            firstName: this.validateForm.value.firstName,
            lastName: this.validateForm.value.lastName,
            email: this.validateForm.value.email,
            password: this.validateForm.value.password
        };
        this.userService.signup(userModel).pipe().subscribe({
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
            nzContent: 'User Created Successfuly',
            nzOnOk: ()=> window.location.reload()
        });
    }
}