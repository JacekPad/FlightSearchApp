import { IAiport } from "./airport-model";
import { FlightClassTypes } from "./enums/flight-class-types";
import { IFlightDetails } from "./flight-details";
import { IFLightRoutePassanger } from "./flight-route-passanger";

export interface IFlightRoute {
    id: number
    flights: IFlightDetails[],
    departureAirport: IAiport,
    arrivalAirport: IAiport,
    seatsLeft: number,
    prices: Map<string, number>,
    departureTime: Date,
    arrivalTime: Date,
    duration: number,
    stops: number,
    passangers: IFLightRoutePassanger[],
    flightClass: FlightClassTypes
    price: number
}

export class FlightRoute {
    public id: number
    public flights: IFlightDetails[];
    public departureAirport: IAiport;
    public arrivalAirport: IAiport;
    public seatsLeft: number;
    public prices: Map<string, number>;
    public departureTime: Date;
    public arrivalTime: Date;
    public duration: number;
    public stops: number;
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
        this.departureTime = obj && obj.departureTime;
        this.arrivalTime = obj && obj.arrivalTime;
        this.duration = obj && obj.duration;
        this.stops = obj && obj.stops;
        this.passangers = obj && obj.passanger;
        this.flightClass = obj && obj.flightClass;
        this.price = obj && obj.price;
    }
}