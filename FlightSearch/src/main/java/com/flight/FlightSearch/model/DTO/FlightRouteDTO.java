package com.flight.FlightSearch.model.DTO;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

//basic DTO for flightRoute info
@Data
public class FlightRouteDTO {

    String id;
    List<FlightDTO> flights;
    CityDTO departureAirport;
    CityDTO arrivalAirport;
    ZonedDateTime departureTime;
    ZonedDateTime arrivalTime;
    List<FlightPriceDTO> prices;
    Long duration;
    Integer stops;
    Integer seatsLeft;
}
