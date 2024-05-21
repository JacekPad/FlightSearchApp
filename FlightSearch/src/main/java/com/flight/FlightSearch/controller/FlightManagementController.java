package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.DTO.SaveFlightDTO;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.service.AirlineService;
import com.flight.FlightSearch.service.AirportService;
import com.flight.FlightSearch.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/api")
public class FlightManagementController {

    private final FlightService flightService;
    private final AirportService airportService;
    private final AirlineService airlineService;


    @GetMapping("flights")
    public void getFlights() {
//        get list of flights with search params
    }

    @GetMapping("flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable(name = "id") String flightId) {
        log.info("CONTROLLER - getFlightById for id {}, START ", flightId);
        Flight flightById = flightService.findFlightById(flightId);
        log.info("CONTROLLER - getFlightById found {}, END", flightById);
        return new ResponseEntity<>(flightById, HttpStatus.OK);
    }

    @PostMapping("flights")
    public ResponseEntity<Flight> addFlight(@RequestBody Flight flight) {
        log.info("CONTROLLER - addFlight {}, START", flight);
        Flight savedFlight = flightService.saveFlight(flight);
        log.info("CONTROLLER - addFlight: saved flight {}, END", savedFlight);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }

    @PutMapping("flights/{id}")
    public ResponseEntity<Flight> updateFLight(@RequestBody Flight flight,@PathVariable(name = "id") String flightId) {
        if (!flight.getFlightId().equals(flightId)) throw new IllegalArgumentException("Provided flight id is incorrect");
        log.info("CONTROLLER - updateFLight {}, START", flight);
        Flight updatedFlight = flightService.updateFlight(flight);
        log.info("CONTROLLER - updateFlight: updated flight: {}, END", updatedFlight);
        return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
    }

//    @PutMapping("5")
//    public void updateFLightStatus() {
//        update flights status to other than canceled / new
//    }

//    @PutMapping("6")
//    public void cancelFlight() {
//         change flights status to canceled
//    }

    @DeleteMapping("flights/{id}")
    public ResponseEntity<Void> removeFlight(@PathVariable(name = "id") String flightId) {
        log.info("CONTROLLER - remove flight by id {}, START", flightId);
        flightService.deleteFlight(flightId);
        log.info("CONTROLLER - remove flight, removed flight for id {}, END", flightId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @PutMapping("8")
//    public void updatePassengerNumber() {
//        update flights passenger number based on booked flights
//    }

}
