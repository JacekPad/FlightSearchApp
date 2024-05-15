package com.flight.FlightSearch.model;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class FlightRoute {

    String id;
    List<Flight> flights;
    Map<FlightClass, Integer> prices;
    Airport departureAirport;
    Airport arrivalAirport;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    Long duration;
    Integer stops;
    Integer seatsLeft;
}
