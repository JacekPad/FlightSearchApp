package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearch;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.service.FlightRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FlightRouteController {

//TODO change to /api/v1
    private final FlightRouteService flightRouteService;
    @GetMapping("app/routes")
    public FlightRouteSearch getFlightRoutes(@ModelAttribute FlightRouteSearchParams params) {
        log.info("Looking for flight routes with params: {}", params);
        FlightRouteSearch flightRoutes = flightRouteService.prepareFlightRoutes(params);
        log.info("Found routes: {}", flightRoutes);
        return flightRoutes;
    }

    @GetMapping("option/{route}")
    public FlightRouteSearch getFlightRoute(@PathVariable String route) {
        log.info("Getting flight route from redis with id: {}", route);
        FlightRouteSearch flightRoute = flightRouteService.getFlightRouteById(route);
        log.info("Found flight route: {}", flightRoute);
        return flightRoute;
    }

    @GetMapping("option/{route}/booking")
    public FlightRouteBookingDTO getFlightRouteDetails(@PathVariable String route, @RequestParam String routeId) {
        log.info("Getting flight route from redis with id: {}", route);
        FlightRouteBookingDTO flightRoute = flightRouteService.getBookingInfo(route, routeId);
        log.info("Found flight route: {}", flightRoute);
        return flightRoute;
    }
}
