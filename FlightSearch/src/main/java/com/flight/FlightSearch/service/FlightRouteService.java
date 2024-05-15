package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearch;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.enums.FlightClass;


public interface FlightRouteService {

    FlightRouteSearch prepareFlightRoutes(FlightRouteSearchParams params);

    FlightRouteSearch getFlightRouteById(String uuid);

    FlightRouteBookingDTO getBookingInfo(String uuid, String routeId, FlightClass flightClass);
}
