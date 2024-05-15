package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airline;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        LocalDateTime now = LocalDateTime.now();
        List<List<Flight>> flights = new ArrayList<>();
        Airport airport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        dfs(airport, params.getArrivalAirportIata(), 0, flights, new ArrayList<>(), new HashSet<>(), params.getMaxStops(), params.getNoOfSeats(), params.getFlightClass(), now);
        log.info("SERVICE: findFlightRoutes - END - Flights found: {}", flights);
        return flights;
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
