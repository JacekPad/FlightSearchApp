package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.repository.FlightOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class FlightOptionServiceImpl implements FlightOptionService {

    private final FlightOptionRepository flightOptionRepository;

    @Override
    public List<FlightOption> getFlightOptionsByFlightId(String id) {
        return flightOptionRepository.findOptionsByFlightId(id);
    }
}
