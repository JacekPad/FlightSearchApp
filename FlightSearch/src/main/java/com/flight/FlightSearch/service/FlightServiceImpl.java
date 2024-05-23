package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airline;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.FlightRepository;
import com.flight.FlightSearch.utils.mappers.FlightMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightOptionService flightOptionService;
    private final AirportService airportService;
    private final AirlineService airlineService;

    @Override
    public List<Flight> getFlightByDepartureAirport(String departureIata) {
        List<FlightEntity> flightEntity = getFlightEntityByDepartureAirport(departureIata);
        return flightEntity.stream().map(FlightMapper::toFlight).toList();
    }

    @Override
    public List<FlightEntity> getFlightEntityByDepartureAirport(String departureIata) {
        List<FlightEntity> flights = flightRepository.findDepartureAirportConnections(departureIata);
        flights.forEach(flight -> {
            flight.setOptions(flightOptionService.getFlightOptionsByFlightId(flight.getFlightId()));
        });
        return flights;
    }

    @Override
    public List<List<Flight>> findFlights(FlightRouteSearchParams params) {
        log.info("SERVICE: findFlightRoutes - START");
        List<List<Flight>> flights = new ArrayList<>();
        Airport airport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        int seats = calculateSeats(params);
        dfs(airport, params.getArrivalAirportIata(), 0, flights, new ArrayList<>(), new HashSet<>(), params.getMaxStops(), seats, params.getFlightClass(), params.getDepartureDate());
        log.info("SERVICE: findFlightRoutes - END - Flights found: {}", flights);
        return flights;
    }

    @Override
    public Flight saveFlight(Flight flight) {
        log.info("SERVICE - saveFlight, {}, start", flight);
        FlightEntity flightEntity = FlightMapper.toFlightEntity(flight);
        FlightEntity savedEntity = flightRepository.save(flightEntity);
        Flight savedFlight = FlightMapper.toFlight(savedEntity);
        log.info("SERVICE - saveFlight, saved flight {}, END", savedEntity);
        return savedFlight;
    }

    @Override
    public void deleteFlight(String flightId) {
        log.info("SERVICE - deleteFlight for id {}", flightId);
        FlightEntity flight = flightRepository.findById(flightId).orElseThrow(() -> new NoSuchElementException("No flight for provided id"));
        flightRepository.delete(flight);
        log.info("SERVICE - deleteFlight, deleted flight for id {}", flightId);
    }

    @Override
    public Flight updateFlight(Flight flight) {
        log.info("SERVICE - updateFlight {}", flight);
        FlightEntity flightEntity = flightRepository.findById(flight.getFlightId()).orElseThrow(() -> new NoSuchElementException("No flight for provided Id"));
        System.out.println("this?");
        FlightEntity updatedFlightEntity = updateFlightValues(flightEntity, flight);
        flightRepository.save(updatedFlightEntity);
        log.info("SERVICE - updateFlight, updated flight from {}, to {}",flightEntity, updatedFlightEntity);
        return FlightMapper.toFlight(updatedFlightEntity);
    }

    private FlightEntity updateFlightValues(FlightEntity flightEntity, Flight flight) {
        if (flight.getArrivalDate() != null) flightEntity.setArrivalDate(flight.getArrivalDate());
        if (flight.getDepartureDate() != null) flightEntity.setDepartureDate(flight.getDepartureDate());
        if (flight.getAirline() != null) flightEntity.setAirline(flight.getAirline());
        if (flight.getOptions() != null) flightEntity.setOptions(new ArrayList<>(flight.getOptions().values()));
        if (flight.getFrom() != null) flightEntity.setFrom(flight.getFrom());
        if (flight.getTo() != null) flightEntity.setTo(flight.getTo());
        return flightEntity;
    }

    @Override
    public Flight findFlightById(String flightId) {
        log.info("SERVICE - findFlightById for id {}, START", flightId);
        FlightEntity flightEntity = flightRepository.findById(flightId).orElseThrow(() -> new NoSuchElementException("No flight found for provided id"));
        log.info("SERVICE - findFlightById found flight {}, END", flightEntity);
        return FlightMapper.toFlight(flightEntity);
    }

    private int calculateSeats(FlightRouteSearchParams params) {
        return params.getAdult() + params.getChild();
    }

    private void dfs(Airport currentAirport, String arrivalAirportIata, int depth, List<List<Flight>> flights, List<Flight> currPath, HashSet<String> visited, int maxSteps, int seats, FlightClass flightClass, LocalDateTime minDepartureTime) {
        if (arrivalAirportIata.equals(currentAirport.getIata())) {
            flights.add(new ArrayList<>(currPath));
            return;
        }
        if (depth > maxSteps || visited.contains(currentAirport.getIata())) return;
        depth++;
        visited.add(currentAirport.getIata());
        List<Flight> flightByDepartureAirport = getFlightByDepartureAirport(currentAirport.getIata());
        for (Flight flight : flightByDepartureAirport) {
            if (validateDepartureDate(flight.getDepartureDate(), minDepartureTime) && validateFlightOption(flight.getOptions(), seats, flightClass)) {
                Airline airline = airlineService.findByFlightId(flight.getFlightId());
                flight.setAirline(airline);
                currPath.add(flight);
                dfs(flight.getTo(), arrivalAirportIata, depth, flights, currPath, visited, maxSteps, seats, flightClass, flight.getArrivalDate());
                currPath.remove(flight);
            }
        }
        visited.remove(currentAirport.getIata());
    }

    private boolean validateDepartureDate(LocalDateTime departureDate, LocalDateTime minDepartureDate) {
        return departureDate.isAfter(minDepartureDate);
    }

    private boolean validateFlightOption(Map<FlightClass, FlightOption> options, int seats, FlightClass flightClass) {
        return options.containsKey(flightClass) && options.get(flightClass).getSeats() >= seats;
    }
}
