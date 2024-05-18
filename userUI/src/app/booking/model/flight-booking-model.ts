import { IFlightBookingChoice } from "../../flight/model/flight-booking-choice";

export interface IFlightBooking {
    departureFlight: IFlightBookingChoice,
    returnFlight: IFlightBookingChoice
}

export class FlightBooking {
    public departureFlight: IFlightBookingChoice;
    public returnFlight: IFlightBookingChoice;

    constructor();
    constructor(obj: IFlightBooking)
    constructor(obj?: any) {
        this.departureFlight = obj && obj.departureFlight;
        this.returnFlight = obj && obj.returnFlight;

    }
}