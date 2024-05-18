package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.enums.FlightClass;


public interface FlightRouteService {

    FlightRouteDTO searchFlightRoutes(FlightRouteSearchParams params);

    FlightRouteDTO getFlightRouteById(String uuid);

    FlightRouteBookingDTO getBookingInfo(String uuid, String routeId, FlightClass flightClass);
}
