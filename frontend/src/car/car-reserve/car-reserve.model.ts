
export interface Reservation {
    startDate: Date;
    endDate: Date;
    returnDate?: Date;
    //method: string;
    status?: string;
    user: { id: number | undefined};
    car: { plateId: number};
}
