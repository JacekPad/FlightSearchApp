package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airline;
import com.flight.FlightSearch.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AirlineServiceImpl implements AirlineService {

    private final AirlineRepository airlineRepository;

    @Override
    public Airline findByFlightId(String id) {
        return airlineRepository.findAirlineByFlightId(id);
    }
}
