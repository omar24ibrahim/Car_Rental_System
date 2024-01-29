
export interface Visibility {
    addCar(): boolean;
    addOffice(): boolean;
    carDetails(): boolean;
    findCar(): boolean;
    editCar(): boolean;
    reserveCar(): boolean;
    findUser(): boolean;
    getType(): string;
    getStats(): boolean;
    showPayments(): boolean;
    showLogs(): boolean;
    updateCarOffice(): boolean;
}

export class Admin implements Visibility {

    addCar(): boolean {
        return true;
    }

    addOffice(): boolean {
        return true;
    }

    findCar(): boolean {
        return true;
    }

    carDetails(): boolean {
        return true;
    }

    editCar(): boolean {
        return true;
    }

    reserveCar(): boolean {
        return false;
    }

    findUser(): boolean {
        return true;
    }
    
    getType(): string {
        return 'ADMIN';
    }

    getStats(): boolean {
        return true;
    }

    showPayments() {
        return true;
    }

    showLogs() {
        return true;
    }

    updateCarOffice() {
        return true;
    }
}

export class Office implements Visibility {

    addCar(): boolean {
        return true;
    }

    addOffice(): boolean {
        return false;
    }

    findCar(): boolean {
        return true;
    }

    carDetails(): boolean {
        return true;
    }

    editCar(): boolean {
        return true;
    }

    reserveCar(): boolean {
        return false;
    }

    findUser(): boolean {
        return false;
    }

    getType(): string {
        return 'OFFICE';
    }

    getStats(): boolean {
        return false;
    }

    showPayments() {
        return false;
    }

    showLogs() {
        return false;
    }

    updateCarOffice() {
        return false;
    }
}

export class Client implements Visibility {

    addCar(): boolean {
        return false;
    }

    addOffice(): boolean {
        return false;
    }

    findCar(): boolean {
        return true;
    }

    carDetails(): boolean {
        return true;
    }

    editCar(): boolean {
        return false;
    }

    reserveCar(): boolean {
        return true;
    }

    findUser(): boolean {
        return false;
    }
    
    getType(): string {
        return 'CLIENT';
    }

    getStats(): boolean {
        return false;
    }

    showPayments() {
        return false;
    }

    showLogs() {
        return false;
    }

    updateCarOffice() {
        return false;
    }
}