package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.service.FlightService;
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


    private final FlightService flightService;

    @GetMapping("test")
    public List<FlightRouteDTO> getFlightRoutes(@ModelAttribute FlightRouteSearchParams params) {
        log.info("looking for flight routes with params: {}", params);
        List<FlightRouteDTO> flightRoutes = flightService.prepareFlightRoutes(params);
        return flightRoutes;
    }

    @GetMapping("10")
    public FlightRouteDTO getFlightRoute(@RequestParam String uuid) {
        return flightService.getVal(uuid);
//        get one flight route for booking from redis database (saved during flight route query)
    }

}
