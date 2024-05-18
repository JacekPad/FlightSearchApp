
export interface IFlightBookingChoice {
    flightClass: string,
    ticketPrice: number,
    routeId: string,
    flightId: string
}

export class FlightBookingChoice {
    public flightClass: string;
    public ticketPrice: number;
    public routeId: string;
    public flightId: string;

    constructor();
    constructor(obj: IFlightBookingChoice)
    constructor(obj?: any) {
        this.flightClass = obj && obj.flightClass;
        this.ticketPrice = obj && obj.ticketPrice;
        this.routeId = obj && obj.routeId;
        this.flightId = obj && obj.flightId;
    }
}