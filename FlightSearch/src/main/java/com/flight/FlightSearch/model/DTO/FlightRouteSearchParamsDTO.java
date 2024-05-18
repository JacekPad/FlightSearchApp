package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.FlightType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRouteSearchParamsDTO {

    private FlightClass flightClass;
    private String departureAirportIata;
    private String arrivalAirportIata;
    private String departureDate;
    private String returnDate;
    private FlightType flightType;
    private int maxStops;
    private int adult;
    private int child;
    private int infant;
}
