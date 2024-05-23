package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.entity.FlightEntity;

import java.util.List;

public interface FlightService {

    List<Flight> getFlightByDepartureAirport(String departureIata);

    List<FlightEntity> getFlightEntityByDepartureAirport(String departureIata);

    List<List<Flight>> findFlights(FlightRouteSearchParams params);

    Flight saveFlight(Flight flight);

    void deleteFlight(String flightId);

    Flight updateFlight(Flight flight);

    Flight findFlightById(String flightId);
}
