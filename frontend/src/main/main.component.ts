import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { LoginComponent } from "./login/login.component";
import { RegisterComponent } from "./register/register.component";


@Component({
    selector: 'app-main',
    templateUrl: './main.component.html',
    styleUrl: './main.component.css',
    standalone: true,
    imports: [CommonModule, LoginComponent, RegisterComponent],
    providers: []
})
export class MainComponent {
    
    isRegister : boolean = false;

    ngOnInit() {}

    constructor() {}

    flipRegister() {
        this.isRegister = !this.isRegister;
    }
}