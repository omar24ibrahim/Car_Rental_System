import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarReservationService } from '../car/car-reserve/car-reserve.service';
import { NzTableModule } from 'ng-zorro-antd/table'
import { NzButtonModule } from 'ng-zorro-antd/button'
import { NzDividerModule } from 'ng-zorro-antd/divider'
import { NzSpaceModule } from 'ng-zorro-antd/space'
import { NzInputModule } from 'ng-zorro-antd/input'
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker'
import { NzModalService } from 'ng-zorro-antd/modal'
import { FormsModule } from '@angular/forms';
import { ReservationRequest } from '../models/reservation-request.model';

@Component({
    selector: 'app-payment',
    standalone: true,
    imports: [CommonModule, NzTableModule, NzDividerModule, NzButtonModule, NzSpaceModule, NzInputModule,
        FormsModule, NzDatePickerModule],
    providers: [CarReservationService, NzModalService],
    templateUrl: './payment.component.html',
    styleUrls: ['./payment.component.css']
})
export class PaymentComponent {

    payments: any[];
    startDate: Date | null = null;
    endDate: Date | null = null;
    loading: boolean;

    constructor(private reservationService: CarReservationService,
        private modal: NzModalService) { }

    ngOnInit() {
    }

    search() {
        this.startDate?.setHours(0, 0, 0);
        this.endDate?.setHours(23, 59, 59);
        const request: ReservationRequest = {
            email: null,
            plateId: null,
            startDate: this.startDate,
            endDate: this.endDate
        }
        this.reservationService.getAllPayments(request).pipe().subscribe({
            next: (response) => {
                this.payments = response;
                this.endDate = null;
                this.startDate = null;
            },
            error: (error) => this.error(error.error)
        });
    }

    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }
}
