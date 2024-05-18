package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParamsDTO;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.service.FlightRouteService;
import com.flight.FlightSearch.utils.mappers.FlightRouteSearchParamsMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class FlightRouteController {

    private final FlightRouteService flightRouteService;
    @GetMapping("routes")
    public FlightRouteDTO getFlightRoutes(@ModelAttribute FlightRouteSearchParamsDTO paramsDTO) {
        log.info("Looking for flight routes with params: {}", paramsDTO);
        FlightRouteSearchParams params = FlightRouteSearchParamsMapper.fromDTO(paramsDTO);
        FlightRouteDTO flightRouteDTO = flightRouteService.searchFlightRoutes(params);
        log.info("Found routes: {}", flightRouteDTO);
        return flightRouteDTO;
    }

    @GetMapping("option/{route}")
    public FlightRouteDTO getFlightRoute(@PathVariable String route) {
        log.info("Getting flight route from redis with id: {}", route);
        FlightRouteDTO flightRoute = flightRouteService.getFlightRouteById(route);
        log.info("Found flight route: {}", flightRoute);
        return flightRoute;
    }

    @GetMapping("option/{route}/booking")
    public FlightRouteBookingDTO getFlightRouteDetails(@PathVariable String route, @RequestParam String routeId, @RequestParam FlightClass flightClass) {
        log.info("Getting flight route from redis with id: {} for class: {}", route, flightClass);
        FlightRouteBookingDTO flightRoute = flightRouteService.getBookingInfo(route, routeId, flightClass);
        log.info("Found flight route: {}", flightRoute);
        return flightRoute;
    }
}
