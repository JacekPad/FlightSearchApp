import { FlightClassTypes } from "./enums/flight-class-types";
import { FlightDetails, IFlightDetails } from "./flight-details";
import { FlightPricingOption, IFlightPricingOption } from "./flight-pricing-option";
import { IFLightRoutePassanger } from "./flight-route-passanger";

// will be two different object, for search and for booking, for now theres one for ease of access and testing

export interface IFlightRoute {
    id: number
    flights: IFlightDetails[],
    departureAirport: string,
    arrivalAirport: string
    seatsLeft: number,
    prices: IFlightPricingOption[],
    departureDate: Date,
    arrivalDate: Date,
    duration: number,
    numOfStops: number,
    passangers: IFLightRoutePassanger[],
    flightClass: FlightClassTypes
    price: number
}

export class FlightRoute {
    public id: number
    public flights: IFlightDetails[];
    public departureAirport: string;
    public arrivalAirport: string;
    public seatsLeft: number;
    public prices: IFlightPricingOption[];
    public departureDate: Date;
    public arrivalDate: Date;
    public duration: number;
    public numOfStops: number;
    public passangers: IFLightRoutePassanger[];
    public flightClass: FlightClassTypes
    public price: number

    constructor();
    constructor(obj: IFlightRoute);
    constructor(obj?: any) {
        this.id = obj && obj.id;
        this.flights = obj && obj.flight;
        this.departureAirport = obj && obj.departureAirport;
        this.arrivalAirport = obj && obj.arrivalAirport;
        this.seatsLeft = obj && obj.seatsLeft;
        this.prices = obj && obj.prices;
        this.departureDate = obj && obj.departureDate;
        this.arrivalDate = obj && obj.arrivalDate;
        this.duration = obj && obj.duration;
        this.numOfStops = obj && obj.numOfStops;
        this.passangers = obj && obj.passanger;
        this.flightClass = obj && obj.flightClass;
        this.price = obj && obj.price;
    }
}