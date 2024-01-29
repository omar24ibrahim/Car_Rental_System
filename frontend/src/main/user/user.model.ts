

export interface UserModel {
    firstName: string | undefined;
    lastName: string | undefined;
    email: string | undefined;
    password: string | undefined;
    userRole?: string | undefined;
    id? : number | undefined;
}