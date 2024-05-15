import { IFLightRoutePassanger } from "./flight-route-passanger";
import { IFlightRoute } from "./flight-routes";

export interface IFlightRouteBooking {
    route: IFlightRoute,
    passengers: IFLightRoutePassanger[],
    price: number
}

export class FlightRouteBooking {
    public flightRoute: IFlightRoute;
    public passengers: IFLightRoutePassanger[];
    public price: number

    constructor()
    constructor(obj: IFlightRouteBooking)
    constructor(obj?: any) {
        this.flightRoute = obj && obj.flightRoute;
        this.passengers = obj && obj.passanger;
        this.price = obj && obj.price;
    }
}