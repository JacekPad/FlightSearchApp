package com.flight.FlightSearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FlightManagementController {

    @GetMapping()
    public void getFlights() {
//        get list of flights with search params
    }

    @GetMapping()
    public void getFlight() {
//        getFLightById
    }

    @PostMapping()
    public void addFlight() {
//        add new flight
    }

    @PutMapping()
    public void updateFLight() {
//        update flights parameters
    }

    @PutMapping()
    public void updateFLightStatus() {
//        update flights status to other than canceled / new
    }

    @PutMapping()
    public void cancelFlight() {
//         change flights status to canceled
    }

    @DeleteMapping()
    public void removeFlight() {
//        remove flight from the list
    }

    @PutMapping()
    public void updatePassengerNumber() {
//        update flights passenger number based on booked flights
    }

}
