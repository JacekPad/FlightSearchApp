package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearch;
import com.flight.FlightSearch.model.DTO.PassengerDTO;
import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.PassengerEnum;
import com.flight.FlightSearch.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${app.config.flight-route.max-adult}")
    private Integer MAX_ADULT;

    @Value("${app.config.flight-route.max-child}")
    private Integer MAX_CHILD;

    @Value("${app.config.flight-route.max-infant}")
    private Integer MAX_INFANT;

    @Override
    public FlightRouteSearch prepareFlightRoutes(FlightRouteSearchParams params) {
        log.info("FlightRouteService - prepareFlightRoutes with params: {}", params);
        Airport departureAirport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        Airport arrivalAirport = airportService.findAirportByIataCode(params.getArrivalAirportIata());
        if (departureAirport == null || arrivalAirport == null) {
            throw new IllegalArgumentException("No airport found");
        }
        validateSearchParameters(params);
        List<FlightRoute> routes = new ArrayList<>();
        List<List<Flight>> flightPaths = flightService.findFlights(params);
//        make method for it
        for (List<Flight> flightPath : flightPaths) {
            FlightRoute route = new FlightRoute();
            route.setId(UUID.randomUUID().toString());
            route.setFlights(flightPath);
            route.setDepartureAirport(departureAirport);
            route.setDepartureTime(flightPath.get(0).getDepartureDate());
            route.setArrivalAirport(arrivalAirport);
            route.setArrivalTime(flightPath.get(flightPath.size() - 1).getArrivalDate());
            route.setSeatsLeft(calculateRouteSeatsLeft(params.getFlightClass(), flightPath));
            route.setDuration(calculateRouteDuration(flightPath));
            route.setStops(flightPath.size());
            route.setPrices(calculateRoutePrice(flightPath));
            routes.add(route);
        }

        FlightRouteSearch flightRouteSearch = new FlightRouteSearch();
        flightRouteSearch.setRoutes(routes);
        flightRouteSearch.setPassengers(mapPassengers(params));
        flightRouteRepository.save(flightRouteSearch);
        log.info("Found flight routes: {}", flightRouteSearch);
        return flightRouteSearch;
    }

    @Override
    public FlightRouteSearch getFlightRouteById(String uuid) {
        return flightRouteRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("No flight route found"));
    }

    @Override
    public FlightRouteBookingDTO getBookingInfo(String uuid, String routeId, FlightClass flightClass) {
        log.info("FlightRouteService Start - getting cached info for id: {} and route: {}, for class: {}", uuid, routeId, flightClass);
        FlightRouteBookingDTO flightRouteBookingDTO = new FlightRouteBookingDTO();
        FlightRouteSearch flightRouteSearch = flightRouteRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("No flight route found"));
        FlightRoute flightRoute = findRouteById(flightRouteSearch.getRoutes(), routeId);
        flightRouteBookingDTO.setRoute(flightRoute);
        flightRouteBookingDTO.setPassengers(flightRouteSearch.getPassengers());
        flightRouteBookingDTO.setPrice(flightRoute.getPrices().get(flightClass));
        log.info("found info: {}", flightRouteBookingDTO);
        return flightRouteBookingDTO;
    }

    private void validateSearchParameters(FlightRouteSearchParams params) {
//        TODO
        if (true) {
//            id date is earlier than today throw exception
        }

        if (params.getAdult() == 0) {
            throw new IllegalArgumentException("Must include at least one adult passenger");
        }

        if (params.getAdult() > MAX_ADULT || params.getChild() > MAX_CHILD || params.getInfant() > MAX_INFANT) {
            throw new IllegalArgumentException("Unacceptable number of passengers");
        }

    }

    private FlightRoute findRouteById(List<FlightRoute> list, String id) {
        return list.stream().filter(d -> id.equals(d.getId())).findFirst().orElseThrow(() -> new NoSuchElementException("No flight route found"));
    }

    private List<PassengerDTO> mapPassengers(FlightRouteSearchParams params) {
        List<PassengerDTO> passengers = new ArrayList<>();
        passengers.add(new PassengerDTO(PassengerEnum.ADULT, params.getAdult()));
        passengers.add(new PassengerDTO(PassengerEnum.CHILD, params.getChild()));
        passengers.add(new PassengerDTO(PassengerEnum.INFANT, params.getInfant()));
        return passengers;
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
                prices.put(c, sum);
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
                .min().orElseThrow(() -> new NoSuchElementException("Can not calculate empty seats"));
    }

}
