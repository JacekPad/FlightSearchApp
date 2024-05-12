package com.flight.FlightSearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FlightRouteController {

    @GetMapping("9")
    public void getFlightRoutes() {
//    initial get flights from search params
    }

    @GetMapping("10")
    public void getFlightRoute() {
//        get one flight route for booking from redis database (saved during flight route query)
    }


}
