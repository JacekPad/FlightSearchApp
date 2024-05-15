import { IAirline } from "./airline-model";
import { IAiport } from "./airport-model";
import { FlightPricingOption } from "./flight-pricing-option";

export interface IFlightDetails {
    flightId: number,
    from: IAiport,
    to: IAiport,
    pricing: FlightPricingOption[];
    airline: IAirline,
    airplaneType: string,
    seatsLeft: number,
    departureDate: Date,
    arrivalDate: Date,
    duration: number
}

export class FlightDetails {
    public flightId: number;
    public from: IAiport;
    public to: IAiport;
    public pricing: FlightPricingOption[];
    public airline: IAirline;
    public airplaneType: string;
    public seatsLeft: number;
    public departureDate: Date;
    public arrivalDate: Date;
    public duration: number;

    constructor()
    constructor(obj: IFlightDetails)
    constructor(obj?: any) {
        this.flightId = obj && obj.flightId;
        this.from = obj && obj.from;
        this.to = obj && obj.to;
        this.pricing = obj && obj.pricing;
        this.airline = obj && obj.airline;
        this.airplaneType = obj && obj.airplaneType;
        this.seatsLeft = obj && obj.seatsLeft;
        this.departureDate = obj && obj.departureDate;
        this.arrivalDate = obj && obj.arrivalDate;
        this.duration = obj && obj.duration;
    }
}