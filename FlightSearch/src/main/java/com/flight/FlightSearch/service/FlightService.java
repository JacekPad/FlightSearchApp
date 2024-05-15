package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.entity.FlightEntity;

import java.util.List;

public interface FlightService {

    List<Flight> getFlightByDepartureAirport(String departureIata);
    List<FlightEntity> getFlightEntityByDepartureAirport(String departureIata);
    List<List<Flight>> findFlights(FlightRouteSearchParams params);
}
