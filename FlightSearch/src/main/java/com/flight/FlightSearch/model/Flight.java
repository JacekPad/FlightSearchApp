package com.flight.FlightSearch.model;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Flight {

    String flightId;
    Airport from;
    Airport to;
    Map<FlightClass, FlightOption> options;
    Airline airline;
    LocalDateTime arrivalDate;
    LocalDateTime departureDate;
}
