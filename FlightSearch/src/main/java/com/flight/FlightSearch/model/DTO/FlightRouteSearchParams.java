package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRouteSearchParams {

    private FlightClass flightClass;
    private String departureAirportIata;
    private String arrivalAirportIata;
    private LocalDateTime departureDate;
    private int maxStops;
    private int adult;
    private int child;
    private int infant;
}
