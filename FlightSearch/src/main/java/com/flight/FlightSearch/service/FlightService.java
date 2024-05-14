package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;

import java.util.List;

public interface FlightService {

    List<FlightRouteDTO> prepareFlightRoutes(FlightRouteSearchParams params);
}
