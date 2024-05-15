package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;

import java.util.List;

public interface FlightRouteService {

    List<FlightRoute> prepareFlightRoutes(FlightRouteSearchParams params);

    FlightRoute getFlightRouteById(String uuid);
}
