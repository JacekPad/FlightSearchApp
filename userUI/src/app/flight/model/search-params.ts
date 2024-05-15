import { FlightClassTypes } from "./enums/flight-class-types";
import { FlightTypes } from "./enums/flight-types";

export interface ISearchParams {
    flightType: FlightTypes,
    flightClass: FlightClassTypes,
    maxStops: number,
    departureAirport: string,
    arrivalAirport: string,
    departureDate: Date,
    returnDate: Date,
    adultNumber: number,
    childNumber: number,
    infantNumber: number
}

export class SearchParams {
    public flightType: FlightTypes;
    public flightClass: FlightClassTypes;
    public maxStops: number;
    public departureAirport: string;
    public arrivalAirport: string;
    public departureDate: Date;
    public returnDate: Date;
    public adultNumber: number;
    public childNumber: number;
    public infantNumber: number;

    constructor()
    constructor(obj: ISearchParams)
    constructor(obj?: any) {
        this.flightType = obj && obj.flightType;
        this.flightClass = obj && obj.flightClass;
        this.maxStops= obj && obj.maxStops;
        this.departureAirport = obj && obj.departureAirport;
        this.arrivalAirport = obj && obj.arrivalAirport;
        this.departureDate = obj && obj.departureDate;
        this.returnDate = obj && obj.returnDate;
        this.adultNumber = obj && obj.adultNumber;
        this.childNumber = obj && obj.childNumber;
        this.infantNumber = obj && obj.infantNumber;
    }
}