package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.CityEntity;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.entity.FlightOptionEntity;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

//basic DTO for flightRoute info
@Data
public class FlightRouteDTO {

    String id;
    List<FlightEntity> flights;
    AirportEntity departureAirport;
    AirportEntity arrivalAirport;
    ZonedDateTime departureTime;
    ZonedDateTime arrivalTime;
    Long duration;
    Integer stops;
    Integer seatsLeft;
}
