package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.entity.AirlineEntity;
import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.entity.FlightOptionEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.AirlineRepository;
import com.flight.FlightSearch.repository.AirportRepository;
import com.flight.FlightSearch.repository.FlightOptionRepository;
import com.flight.FlightSearch.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightOptionRepository flightOptionRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;

    @Override
    public List<List<FlightEntity>> findFlightRoutes(String fromCityIata, String toCityIata, int steps, int seatsRequired, String flightClass) {
        log.info("SERVICE - START");
        List<List<FlightEntity>> flights = new ArrayList<>();
        AirportEntity airport = airportRepository.findByIata(fromCityIata);
        dfs(airport, toCityIata, 0, flights, new ArrayList<>(), new HashSet<>(), steps, seatsRequired, flightClass);
        log.info("Flights found: {}", flights);
        return flights;
    }

    private void dfs(AirportEntity currentAirport, String arrivalAirportIata, int depth, List<List<FlightEntity>> flights, List<FlightEntity> currPath, HashSet<String> visited, int maxSteps, int seats, String flightClass) {
        if (arrivalAirportIata.equals(currentAirport.getIata())) {
            flights.add(new ArrayList<>(currPath));
            return;
        }
        if (depth > maxSteps || visited.contains(currentAirport.getIata())) return;
        depth++;
        visited.add(currentAirport.getIata());
        List<FlightEntity> departureAirportConnections = flightRepository.findDepartureAirportConnections(currentAirport.getIata());
        for (FlightEntity flight : departureAirportConnections) {
//            TODO move to optionsService
            List<FlightOptionEntity> optionsByFlightId = flightOptionRepository.findOptionsByFlightId(flight.getFlightId());
//            TODO move to airline service
            AirlineEntity airline = airlineRepository.findAirlineByFlightId(flight.getFlightId());
            flight.setOptions(optionsByFlightId);
            flight.setAirline(airline);
//            TODO move to different validation method
            if (validateFlightClass(optionsByFlightId, seats, flightClass)) {
                currPath.add(flight);
                dfs(flight.getTo(), arrivalAirportIata, depth, flights, currPath, visited, maxSteps, seats, flightClass);
                currPath.remove(flight);
            }
        }
        visited.remove(currentAirport.getIata());
    }

    private boolean validateFlightClass(List<FlightOptionEntity> options, int seats, String flightClass) {
        log.info("OPTIONS: {}", options);
        long count = options.stream().filter(flightClasses -> flightClasses.getFlightClass().equals(flightClass)).filter(seatCheck -> seatCheck.getSeats() >= seats).count();
        log.info(String.valueOf(count));
        return true;
//        return count > 0;
    }
}
