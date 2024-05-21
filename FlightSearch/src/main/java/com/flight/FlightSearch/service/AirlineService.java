package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airline;

public interface AirlineService {

    Airline findByFlightId(String id);

    Airline findByIataCode(String iataCode);
}
