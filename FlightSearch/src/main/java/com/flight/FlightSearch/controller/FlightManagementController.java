package com.flight.FlightSearch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FlightManagementController {

    @GetMapping("1")
    public void getFlights() {
//        get list of flights with search params
    }

    @GetMapping("2")
    public void getFlight() {
//        getFLightById
    }

    @PostMapping("3")
    public void addFlight() {
//        add new flight
    }

    @PutMapping("4")
    public void updateFLight() {
//        update flights parameters
    }

    @PutMapping("5")
    public void updateFLightStatus() {
//        update flights status to other than canceled / new
    }

    @PutMapping("6")
    public void cancelFlight() {
//         change flights status to canceled
    }

    @DeleteMapping("7")
    public void removeFlight() {
//        remove flight from the list
    }

    @PutMapping("8")
    public void updatePassengerNumber() {
//        update flights passenger number based on booked flights
    }

}
