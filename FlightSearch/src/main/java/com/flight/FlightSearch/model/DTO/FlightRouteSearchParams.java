package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

@Data
public class FlightRouteSearchParams {

    private FlightClass flightClass;
    private String departureAirportIata;
    private String arrivalAirportIata;
    private int maxStops;
    private int adult;
    private int child;
    private int infant;
}
