package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.entity.FlightEntity;

import java.util.List;

public interface FlightService {

    List<List<FlightEntity>> findFlightRoutes(String fromCityIata, String toCityIata, int steps, int seatsRequired, String flightClass);
}
