package com.flight.FlightSearch.model;

import com.flight.FlightSearch.model.entity.AirlineEntity;
import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.FlightOptionEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Flight {

    String flightId;
    AirportEntity from;
    AirportEntity to;
    Map<FlightClass, FlightOptionEntity> options;
    AirlineEntity airline;
    LocalDateTime arrivalDate;
    LocalDateTime departureDate;
}
