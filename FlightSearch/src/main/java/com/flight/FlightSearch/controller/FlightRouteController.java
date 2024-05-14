package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.entity.*;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.AirportRepository;
import com.flight.FlightSearch.repository.CityRepository;
import com.flight.FlightSearch.repository.CountryRepository;
import com.flight.FlightSearch.repository.FlightRepository;
import com.flight.FlightSearch.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FlightRouteController {


    private final FlightService flightService;

    @GetMapping("test")
    public List<List<FlightEntity>> getFlightRoutes(@RequestParam String fromIata, @RequestParam String toIata, @RequestParam int steps, @RequestParam int seats, @RequestParam String flightClass) {
        log.info("looking for flight routes with params: {}, {}, {}, {}", fromIata, toIata, steps, seats);
        List<List<FlightEntity>> flightRoutes = flightService.findFlightRoutes(fromIata, toIata, steps, seats, flightClass);
        return flightRoutes;
//    initial get flights from search params
    }

    @GetMapping("10")
    public void getFlightRoute() {
//        get one flight route for booking from redis database (saved during flight route query)
    }

}
