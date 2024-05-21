package com.flight.FlightSearch.model;

import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.FlightType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRouteSearchParams {

    private FlightClass flightClass;
    private String departureAirportIata;
    private String arrivalAirportIata;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private FlightType flightType;
    private int maxStops;
    private int adult;
    private int child;
    private int infant;
}
