package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.FlightOption;

import java.util.List;

public interface FlightOptionService {

    List<FlightOption> getFlightOptionsByFlightId(String id);
}

