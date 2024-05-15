package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.service.FlightRouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FlightRouteController {


    private final FlightRouteService flightRouteService;

    @GetMapping("test")
    public List<FlightRoute> getFlightRoutes(@ModelAttribute FlightRouteSearchParams params) {
        log.info("looking for flight routes with params: {}", params);
        List<FlightRoute> flightRoutes = flightRouteService.prepareFlightRoutes(params);
        return flightRoutes;
    }

    @GetMapping("10")
    public FlightRoute getFlightRoute(@RequestParam String uuid) {
        return flightRouteService.getFlightRouteById(uuid);
//        get one flight route for booking from redis database (saved during flight route query)
    }

}
