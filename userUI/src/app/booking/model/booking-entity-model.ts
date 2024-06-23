import { IFlightEntity } from "./flight-model";
import { IPassenger } from "./passenger-model";

export interface IBookingEntity {
    id: number,
    email: string,
    passengers: IPassenger[],
    payMethod: string,
    flights: IFlightEntity[],
    phoneNumber: string
    
}

export class BookingEntity {
    public id: number;
    public email: string;
    public passengers: IPassenger[];
    public payMethod: string;
    public flights: IFlightEntity[];
    public phoneNumber: string

    constructor();
    constructor(obj: IBookingEntity)
    constructor(obj?: any) {
        this.id = obj && obj.id;
        this.email = obj && obj.email;
        this.passengers = obj && obj.passanger;
        this.payMethod = obj && obj.payMethod;
        this.flights = obj && obj.flight;
        this.phoneNumber = obj && obj.phoneNumber;
    }
}