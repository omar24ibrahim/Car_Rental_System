import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Login } from './login.model';
import { environment } from '../../enviroment/enviroment';
import { Observable, map } from 'rxjs';



@Injectable({
  providedIn: 'root'
})

export class LoginService {

  constructor(private http: HttpClient) { }

  

}
