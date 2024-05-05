import { FlightClassTypes } from "./enums/flight-class-types";

export interface IFlightPricingOption {
    flightClass: FlightClassTypes,
    price: number
}

export class FlightPricingOption {
    public flightClass: FlightClassTypes
    public price: number

    constructor();
    constructor(obj: IFlightPricingOption)
    constructor(obj?: any) {
        this.flightClass = obj && obj.flightClass;
        this.price = obj && obj.price;
    }
}