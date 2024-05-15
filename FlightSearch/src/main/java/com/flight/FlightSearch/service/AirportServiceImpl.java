package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirportServiceImpl implements AirportService{

    private final AirportRepository airportRepository;
    @Override
    public Airport findAirportByIataCode(String iata) {
        return airportRepository.findByIata(iata);
    }
}
