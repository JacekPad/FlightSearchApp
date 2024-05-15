package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airport;

public interface AirportService {

    Airport findAirportByIataCode(String iata);
}
