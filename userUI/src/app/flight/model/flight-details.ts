import { FlightPricingOption } from "./flight-pricing-option";

export interface IFlightDetails {
    id: number,
    departureAirport: string,
    arrivalAirpot: string,
    pricing: FlightPricingOption[];
    airline: string,
    airplaneType: string,
    seatsLeft: number,
    departureDate: Date,
    arrivalDate: Date,
    duration: number
}

export class FlightDetails {
    public id: number;
    public departureAirport: string;
    public arrivalAirpot: string;
    public pricing: FlightPricingOption[];
    public airline: string;
    public airplaneType: string;
    public seatsLeft: number;
    public departureDate: Date;
    public arrivalDate: Date;
    public duration: number;

    constructor()
    constructor(obj: IFlightDetails)
    constructor(obj?: any) {
        this.id = obj && obj.id;
        this.departureAirport = obj && obj.departureAirport;
        this.arrivalAirpot = obj && obj.arrivalAirpot;
        this.pricing = obj && obj.pricing;
        this.airline = obj && obj.airline;
        this.airplaneType = obj && obj.airplaneType;
        this.seatsLeft = obj && obj.seatsLeft;
        this.departureDate = obj && obj.departureDate;
        this.arrivalDate = obj && obj.arrivalDate;
        this.duration = obj && obj.duration;
    }
}