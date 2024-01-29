import { HttpClient } from "@angular/common/http";
import { Inject, Injectable, PLATFORM_ID } from "@angular/core";
import { UserModel } from "./user.model";
import { Observable } from "rxjs";
import { LoginRequest } from "../login/login-request.model";
import { environment } from "../../enviroment/enviroment";
import { isPlatformBrowser } from "@angular/common";
import { Admin, Client, Office, Visibility } from "../../models/roles.model";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiServerUrl = environment.apiBaseUrl + "user";

    isBrowser: boolean;

    constructor(private http: HttpClient, @Inject(PLATFORM_ID) platformId: Object) {
        this.isBrowser = isPlatformBrowser(platformId);
     }

    public signup(userModel: UserModel): Observable<string> {
        return this.http.post(`${this.apiServerUrl}/signup`, userModel, {responseType: 'text'});
    }

    public login(loginRequest: LoginRequest): Observable<any> {
        return this.http.post(`${this.apiServerUrl}/login`, loginRequest, {responseType: 'text'});
    }

    public getUsersByAttribute(attributeName: string, attributeValue: string): Observable<UserModel[]> {
        return this.http.get<UserModel[]>(`${this.apiServerUrl}/find/${attributeName}/${attributeValue}`);
    }

    public getAllUsers(): Observable<UserModel[]> {
        return this.http.get<UserModel[]>(`${this.apiServerUrl}/find/all`);
    }

    public setUser(user: UserModel) {
        if(this.isBrowser){
            sessionStorage.setItem("user", JSON.stringify(user));
        } 
    }

    public getUser(): UserModel | null {
        if(this.isBrowser){
            let response: any = sessionStorage.getItem("user");
            if (!response) response = "";
            let user: UserModel = JSON.parse(response);
            if (typeof user === 'string') {
            user = JSON.parse(user);
              }
            return user;
        }
        return null;   
    }

    public getVisibility(): Visibility | undefined {
        const user: UserModel | null = this.getUser();
        let visibility: Visibility;
        if (!user) return undefined;
        if (user.userRole === 'ADMIN') {
            visibility = new Admin();
        } else if (user.userRole === 'OFFICE') {
            visibility = new Office();
        } else {
            visibility = new Client();
        }
        return visibility;
    }

    public clearUser() {
        sessionStorage.removeItem("user");
    }
}