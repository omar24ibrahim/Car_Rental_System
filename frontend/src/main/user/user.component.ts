import { Component, Input, OnInit } from "@angular/core";
import { CarService } from "../../car/car.service";
import { CommonModule } from "@angular/common";
import { NzCardComponent, NzCardMetaComponent } from 'ng-zorro-antd/card'
import { NzAvatarComponent } from 'ng-zorro-antd/avatar'
import { NzIconModule } from "ng-zorro-antd/icon";
import { NzDescriptionsModule } from "ng-zorro-antd/descriptions";
import { UserService } from "../user/user.service";
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzPageHeaderModule } from 'ng-zorro-antd/page-header';
import { NzSpaceComponent, NzSpaceItemDirective, NzSpaceModule } from 'ng-zorro-antd/space';
import { NzDescriptionsItemComponent } from 'ng-zorro-antd/descriptions';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { UserModel } from "./user.model";
import { NzDropDownModule } from "ng-zorro-antd/dropdown";
import { NzTableModule } from 'ng-zorro-antd/table';
import {
  FormControl,
  FormGroup,
  FormsModule,
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators
} from '@angular/forms';

@Component({
  selector: 'app-car',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css'],
  standalone: true,
  imports: [CommonModule, NzGridModule, NzPageHeaderModule, ReactiveFormsModule, NzDescriptionsItemComponent, 
    NzSpaceComponent, NzButtonComponent, NzSpaceItemDirective, NzDropDownModule, NzIconModule, NzTableModule],
  providers: [CarService, UserService]
})
export class UserComponent {
    properties: string[] = ['email', 'id', 'name'];
    propertyName: string = '';
    propertyValue: string;
    searchForm: FormGroup<{propertyValue: FormControl<string>}>;
    users: UserModel[] = [];

    constructor(private userService: UserService, private fb: NonNullableFormBuilder) {
        this.searchForm = this.fb.group({propertyValue: ['', [Validators.required]]});
    }

    ngOnInit(): void{
        this.userService.getAllUsers().pipe().subscribe({
            next: (response: UserModel[] | null) => {
              if (response) {
                for(let i = 0; i < response.length; i++) {
                    this.users.push(response[i]);
                }
              } else {
                // Handle the case when the response is null or undefined
                console.log("Cars response is null or undefined");
              }
            },
            error: (error) => console.log(error.error)
          });
    }

    updateSearchBar(propertyName: string): void{
        this.propertyName = propertyName;
    }

    findUsers(): void{
        this.propertyValue = String(this.searchForm.value.propertyValue);
        this.userService.getUsersByAttribute(this.propertyName, this.propertyValue).pipe().subscribe({
            next: (response: UserModel[] | null) => {
              if (response) {
                this.users = [];
                for(let i = 0; i < response.length; i++) {
                    this.users.push(response[i]);
                }
              } else {
                // Handle the case when the response is null or undefined
                console.log("Cars response is null or undefined");
              }
            },
            error: (error) => console.log(error.error)
          });
    }

    resetSearch(): void{
        this.searchForm.reset();
        this.propertyName = '';
        this.userService.getAllUsers().pipe().subscribe({
            next: (response: UserModel[] | null) => {
              if (response) {
                this.users = [];
                for(let i = 0; i < response.length; i++) {
                    this.users.push(response[i]);
                }
              } else {
                // Handle the case when the response is null or undefined
                console.log("Cars response is null or undefined");
              }
            },
            error: (error) => console.log(error.error)
          });
    }
}