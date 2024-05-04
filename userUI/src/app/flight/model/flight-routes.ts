import { FlightDetails } from "./flight-details";

export interface IFlightRoute {
    flights: FlightDetails[],
    departureAirport: string,
    arrivalAirport: string
    seatsLeft: number,
    totalPrice: number,
    departureDate: Date,
    arrivalDate: Date,
    duration: number,
    numOfStops: number
}

export class FlightRoute {
    public flights: FlightDetails[];
    public departureAirport: string;
    public arrivalAirport: string;
    public seatsLeft: number;
    public totalPrice: number;
    public departureDate: Date;
    public arrivalDate: Date;
    public duration: number;
    public numOfStops: number;

    constructor();
    constructor(obj: IFlightRoute);
    constructor(obj?: any) {
        this.flights = obj && obj.flight;
        this.departureAirport = obj && obj.departureAirport;
        this.arrivalAirport = obj && obj.arrivalAirport;
        this.seatsLeft = obj && obj.seatsLeft;
        this.totalPrice = obj && obj.totalPrice;
        this.departureDate = obj && obj.departureDate;
        this.arrivalDate = obj && obj.arrivalDate;
        this.duration = obj && obj.duration;
        this.numOfStops = obj && obj.numOfStops;
    }
}