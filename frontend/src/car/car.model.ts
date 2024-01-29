
export interface Car {
    plateId: number;
    brand: string;
    type: string;
    year: number;
    status: string;
    rate: number;
    transmissionType: string;
    fuelType: string;
    bodyStyle: string;
    color: string;
    capacity: number;
    imageUrl: string;
    office: { officeId: number };
    [key: string]: any; // Index signature allowing any string property
}