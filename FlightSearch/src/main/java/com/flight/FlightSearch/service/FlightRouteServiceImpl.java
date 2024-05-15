package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlightRouteServiceImpl implements FlightRouteService {

    private final FlightRouteRepository flightRouteRepository;
    private final FlightService flightService;
    private final AirportService airportService;

    @Override
    public List<FlightRoute> prepareFlightRoutes(FlightRouteSearchParams params) {
        Airport departureAirport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        Airport arrivalAirport = airportService.findAirportByIataCode(params.getArrivalAirportIata());
        List<FlightRoute> routes = new ArrayList<>();
        List<List<Flight>> flightPaths = flightService.findFlights(params);
        for (List<Flight> flightPath : flightPaths) {
            FlightRoute route = new FlightRoute();
            route.setId(UUID.randomUUID().toString());
            route.setFlights(flightPath);
            route.setDepartureAirport(departureAirport);
            route.setDepartureTime(flightPath.get(0).getDepartureDate());
            route.setArrivalAirport(arrivalAirport);
            route.setArrivalTime(flightPath.get(flightPath.size()-1).getArrivalDate());
            route.setSeatsLeft(calculateRouteSeatsLeft(params.getFlightClass(), flightPath));
            route.setDuration(calculateRouteDuration(flightPath));
            route.setStops(flightPath.size());
            route.setPrices(calculateRoutePrice(flightPath));
            FlightRoute redisCache = flightRouteRepository.save(route);
            routes.add(redisCache);
        }
        return routes;
    }

    @Override
    public FlightRoute getFlightRouteById(String uuid) {
        return flightRouteRepository.findById(uuid).orElseThrow(NoSuchElementException::new);
    }

    private Map<FlightClass, Integer> calculateRoutePrice(List<Flight> flights) {
        Map<FlightClass, Integer> prices = new HashMap<>();
        for (FlightClass c : FlightClass.values()) {
            long count = flights.stream()
                    .map(Flight::getOptions)
                    .filter(s -> s.containsKey(c))
                    .count();
            if (count == flights.size()) {
            int sum = flights.stream()
                    .map(Flight::getOptions)
                    .map(s -> s.get(c))
                    .mapToInt(FlightOption::getPrice)
                    .sum();
            prices.put(c,sum);
            }
        }
        return prices;
    }

    private long calculateRouteDuration(List<Flight> flightPath) {
        LocalDateTime departureDate = flightPath.get(0).getDepartureDate();
        LocalDateTime arrivalDate = flightPath.get(flightPath.size() - 1).getArrivalDate();
        return Duration.between(departureDate, arrivalDate).toMinutes();
    }

    private int calculateRouteSeatsLeft(FlightClass flightClass, List<Flight> flightPath) {
        return flightPath.stream()
                .map(Flight::getOptions)
                .map(f -> f.get(flightClass))
                .mapToInt(FlightOption::getSeats)
                .min().orElseThrow(NoSuchElementException::new);
    }

}
