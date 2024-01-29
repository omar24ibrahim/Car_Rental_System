import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzTableModule } from 'ng-zorro-antd/table'
import { NzButtonModule } from 'ng-zorro-antd/button'
import { NzDividerModule } from 'ng-zorro-antd/divider'
import { NzSpaceModule } from 'ng-zorro-antd/space'
import { NzInputModule } from 'ng-zorro-antd/input'
import { NzDatePickerModule } from 'ng-zorro-antd/date-picker'
import { NzModalService } from 'ng-zorro-antd/modal'
import { FormsModule } from '@angular/forms';
import { StatusLogRequest } from '../models/status-log-request.model';
import { StatusLogService } from './status-log.service';

@Component({
    selector: 'app-reservation',
    standalone: true,
    imports: [CommonModule, NzTableModule, NzDividerModule, NzButtonModule, NzSpaceModule, NzInputModule,
        FormsModule, NzDatePickerModule],
    providers: [StatusLogService, NzModalService],
    templateUrl: './status-log.component.html',
    styleUrls: ['./status-log.component.css']
})
export class StatusLogComponent {

    logs: any[];
    date: Date | null = null;
    loading: boolean;

    constructor(private statusLogService: StatusLogService,
        private modal: NzModalService) { }

    ngOnInit() {
    }

    search() {
        this.date?.setHours(23, 59, 59);
        const request: StatusLogRequest = {
            date: this.date
        }
        this.statusLogService.getStates(request).pipe().subscribe({
            next: (response) => {
                this.logs = response;
                this.loading = false;
                this.date = null
            },
            error: (error) => {
                this.error(error.error);
                this.logs = [];
                this.date = null;
                this.loading = false;
            }
        });
    }

    private error(msg: string): void {
        this.modal.error({
            nzTitle: 'Error',
            nzContent: msg
        });
    }
}
