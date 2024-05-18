import { FlightRoute } from "./flight-routes";

export interface IFlightRouteResponse {
    id: string,
    routeDeparture: FlightRoute[]
    routeReturn: FlightRoute[]
}