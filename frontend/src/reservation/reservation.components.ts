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

export enum SearchModes {
    SEARCH_BY_CAR,
    SEARCH_BY_DATE,
    SEARCH_BY_CLIENT,
    SEARCH_ALL
}

@Component({
    selector: 'app-reservation',
    standalone: true,
    imports: [CommonModule, NzTableModule, NzDividerModule, NzButtonModule, NzSpaceModule, NzInputModule,
        FormsModule, NzDatePickerModule],
    providers: [CarReservationService, NzModalService],
    templateUrl: './reservation.components.html',
    styleUrls: ['./reservation.components.css']
})
export class ReservationComponent {

    reservations: any[];
    searchMode: SearchModes | undefined = undefined;
    plateId: number | null = null;
    clientEmail: string | null = null;
    startDate: Date | null = null;
    endDate: Date | null = null;
    loading: boolean;

    constructor(private reservationService: CarReservationService,
        private modal: NzModalService) { }

    ngOnInit() {
        this.loading = true;
        this.reservationService.getAllReservations().subscribe((response) => {
            this.reservations = response;
            this.loading = false;
        });
    }

    searchByPeriod() {
        this.searchMode = SearchModes.SEARCH_BY_DATE;
    }

    searchByCar() {
        this.searchMode = SearchModes.SEARCH_BY_CAR;
    }

    searchByClient() {
        this.searchMode = SearchModes.SEARCH_BY_CLIENT;
    }

    showAll() {
        this.searchMode = SearchModes.SEARCH_ALL;
        this.reservations = [];
        this.loading = true;
        this.reservationService.getAllReservations().subscribe((response) => {
            this.reservations = response;
            this.loading = false;
        });
    }

    search() {
        if (this.searchMode !== undefined && this.searchMode !== SearchModes.SEARCH_ALL) {
            this.startDate?.setHours(0,0,0);
            this.endDate?.setHours(23,59,59);
            const request: ReservationRequest = {
                email: this.clientEmail,
                plateId: this.plateId,
                startDate: this.startDate,
                endDate: this.endDate
            }
            this.reservationService.searchReservations(request).pipe().subscribe({
                next: (response) => {
                    this.reservations = response;
                    this.loading = false;
                    this.reset();
                }, 
                error: (error) => {
                    this.error(error.error);
                    this.reservations = [];
                    this.loading = false;
                    this.reset();
                }
            });
        }
    }

    private reset() {
        this.clientEmail = null;
        this.plateId = null;
        this.startDate = null;
        this.endDate = null;
    }

    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }
}
