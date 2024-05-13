package com.flight.FlightSearch.controller;

import com.flight.FlightSearch.model.entity.*;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.AirportRepository;
import com.flight.FlightSearch.repository.CityRepository;
import com.flight.FlightSearch.repository.CountryRepository;
import com.flight.FlightSearch.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class FlightRouteController {

    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;

    @GetMapping("9")
    public void getFlightRoutes() {
//    initial get flights from search params
    }

    @GetMapping("10")
    public void getFlightRoute() {
//        get one flight route for booking from redis database (saved during flight route query)
    }

    @GetMapping("test-country")
    public List<CountryEntity> getCountries() {
        List<CountryEntity> all = countryRepository.findAll();
        log.info("Countries: {}", all);
        return all;
    }

    @GetMapping("test-city")
    public List<CityEntity> getCities() {
        List<CityEntity> all = cityRepository.findAll();
        log.info("Cities: {}", all);
        return all;
    }

    @GetMapping("test-airport/{iata}")
    public AirportEntity getAirtposrt(@PathVariable String iata) {
        log.info("iata: {}", iata);
        AirportEntity byIata = airportRepository.findByIata(iata);
        log.info("airports: {}", byIata);
        return byIata;
    }

    @GetMapping("test-flight")
    public List<FlightEntity> getFlights() {
        List<FlightEntity> all = flightRepository.findAll();
        log.info("flights: {}", all);
        return all;
    }

}
