import { FlightDetails, IFlightDetails } from "./flight-details";
import { FlightPricingOption, IFlightPricingOption } from "./flight-pricing-option";

export interface IFlightRoute {
    flights: IFlightDetails[],
    departureAirport: string,
    arrivalAirport: string
    seatsLeft: number,
    prices: IFlightPricingOption[],
    departureDate: Date,
    arrivalDate: Date,
    duration: number,
    numOfStops: number
}

export class FlightRoute {
    public flights: IFlightDetails[];
    public departureAirport: string;
    public arrivalAirport: string;
    public seatsLeft: number;
    public prices: IFlightPricingOption[];
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
        this.prices = obj && obj.prices;
        this.departureDate = obj && obj.departureDate;
        this.arrivalDate = obj && obj.arrivalDate;
        this.duration = obj && obj.duration;
        this.numOfStops = obj && obj.numOfStops;
    }
}