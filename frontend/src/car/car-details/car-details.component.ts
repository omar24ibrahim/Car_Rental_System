import { Component } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Car } from '../../car/car.model';
import { NzDescriptionsModule } from 'ng-zorro-antd/descriptions'
import { NzBadgeModule } from 'ng-zorro-antd/badge'
import {  Router } from "@angular/router";

@Component({
    selector: 'app-car-details',
    templateUrl: './car-details.component.html',
    styleUrl: './car-details.component.css',
    standalone: true,
    imports: [CommonModule, NzDescriptionsModule, NzBadgeModule],
    providers: []
})
export class CarDetailsComponent {
    car: Car;

    constructor(private route: Router) {
    }

    ngOnInit(): void {
        if (typeof history !== 'undefined') {
            this.car = history.state.car;
        }
    }
}