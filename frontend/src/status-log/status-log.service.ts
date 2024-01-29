import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { environment } from "../enviroment/enviroment";
import { StatusLogRequest } from "../models/status-log-request.model";


@Injectable({
  providedIn: 'root'
})
export class StatusLogService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private http: HttpClient) { }

  public getStates(request: StatusLogRequest): Observable<any>{ 
    return this.http.post<any[]>(`${this.apiServerUrl}log`, request);
  }
}