import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from '../admin/admin.component';
import { MainComponent } from '../main/main.component';
import { CarDetailsComponent } from '../car/car-details/car-details.component';
import { OfficeCreateComponent } from '../office/office-create/office-create.component';
import { CarAddComponent } from '../car/car-add/car-add.component';
import { CarEditComponent } from '../car/car-edit/car-edit.component';
import { CarReservationComponent } from '../car/car-reserve/car-reserve.component';
import { UserComponent } from '../main/user/user.component';
import { ReservationComponent } from '../reservation/reservation.components';
import { PaymentComponent } from '../payment/payment.component';
import { StatusLogComponent } from '../status-log/status-log.component';


export const routes: Routes = [
    {
        path: '',
        component: MainComponent
    },
    {
        path: 'car-rental',
        component: AdminComponent
    } ,
    {
        path: 'details',
        component: CarDetailsComponent
    },
    {
        path: 'addCar',
        component: CarAddComponent
    },
    {
        path: 'addOffice',
        component: OfficeCreateComponent
    },
    {
        path: 'editCar',
        component: CarEditComponent
    },
    {
        path: 'reserveCar',
        component: CarReservationComponent
    },
    {
        path: 'findUser',
        component: UserComponent
    },
    {
        path: 'reservation-stats',
        component: ReservationComponent
    },
    {
        path: 'payments',
        component: PaymentComponent
    },
    {
        path: 'logs',
        component: StatusLogComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }
