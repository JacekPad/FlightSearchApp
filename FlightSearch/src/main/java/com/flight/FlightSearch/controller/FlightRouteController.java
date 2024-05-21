package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.FlightRouteSearchParams;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParamsDTO;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.service.FlightRouteService;
import com.flight.FlightSearch.utils.mappers.FlightRouteSearchParamsMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@RequestMapping("api/v1/")
public class FlightRouteController {

    private final FlightRouteService flightRouteService;
    @GetMapping("routes")
    public FlightRouteDTO getFlightRoutes(@Valid @ModelAttribute FlightRouteSearchParamsDTO paramsDTO) {
        log.info("CONTROLLER - getFlightRoutes with params {}, START", paramsDTO);
        FlightRouteSearchParams params = FlightRouteSearchParamsMapper.fromDTO(paramsDTO);
        FlightRouteDTO flightRouteDTO = flightRouteService.searchFlightRoutes(params);
        log.info("CONTROLLER - getFlightRoutes found routes {}, END", flightRouteDTO);
        return flightRouteDTO;
    }

    @GetMapping("option/{route}")
    public FlightRouteDTO getFlightRoute(@PathVariable String route) {
        log.info("CONTROLLER - getFlightRoute with id: {}", route);
        FlightRouteDTO flightRoute = flightRouteService.getFlightRouteById(route);
        log.info("CONTROLLER -getFlightRoute, found flight route: {}", flightRoute);
        return flightRoute;
    }

    @GetMapping("option/{route}/booking")
    public FlightRouteBookingDTO getFlightRouteDetails(@PathVariable String route, @RequestParam String routeId, @RequestParam FlightClass flightClass) {
        log.info("CONTROLLER - getFlightRouteDetails with id: {} for class: {}", route, flightClass);
        FlightRouteBookingDTO flightRoute = flightRouteService.getBookingInfo(route, routeId, flightClass);
        log.info("CONTROLLER - getFlightRouteDetails found flight route: {}", flightRoute);
        return flightRoute;
    }
}
