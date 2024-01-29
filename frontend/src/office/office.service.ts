import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "../enviroment/enviroment";
import { OfficeModel } from "./office.model";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class OfficeService {
    private apiServerUrl = environment.apiBaseUrl + "office";

    constructor(private http: HttpClient) { }

    public addOffice(officeModel: OfficeModel): Observable<string> {
        return this.http.post(`${this.apiServerUrl}/add`, officeModel, {responseType: 'text'});
    }

    public getAllOfficeIds(): Observable<number[]>{
        return this.http.get<number[]>(`${this.apiServerUrl}/find/All/OfficesId`);
    }
}