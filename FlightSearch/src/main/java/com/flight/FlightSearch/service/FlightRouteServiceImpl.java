package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.PassengerDTO;
import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.FlightType;
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
    public FlightRouteDTO searchFlightRoutes(FlightRouteSearchParams params) {
        log.info("FlightRouteService - prepareFlightRoutes with params: {}", params);
        FlightRouteDTO flightRouteDTO = new FlightRouteDTO();
        Airport departureAirport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        Airport arrivalAirport = airportService.findAirportByIataCode(params.getArrivalAirportIata());
        if (departureAirport == null || arrivalAirport == null) {
            throw new IllegalArgumentException("No airport found");
        }
        validateSearchParameters(params);
        List<FlightRoute> routesDeparture = new ArrayList<>();

        List<List<Flight>> flightsDeparture = flightService.findFlights(params);
        prepareFlights(flightsDeparture, params, routesDeparture);
        flightRouteDTO.setRouteDeparture(routesDeparture);

        if (params.getFlightType().equals(FlightType.ROUND)) {
            String departureAirportIata = params.getDepartureAirportIata();
            params.setDepartureAirportIata(params.getArrivalAirportIata());
            params.setArrivalAirportIata(departureAirportIata);
            params.setDepartureDate(params.getReturnDate());
            List<List<Flight>> flightsReturn = flightService.findFlights(params);
            List<FlightRoute> routesReturn = new ArrayList<>();
            prepareFlights(flightsReturn, params, routesReturn);
        flightRouteDTO.setRouteReturn(routesReturn);
        }
        flightRouteDTO.setPassengers(mapPassengers(params));
        flightRouteRepository.save(flightRouteDTO);
        log.info("Found flight routes: {}", flightRouteDTO);
        return flightRouteDTO;
    }

    @Override
    public FlightRouteDTO getFlightRouteById(String uuid) {
        return flightRouteRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("No flight route found"));
    }

    @Override
    public FlightRouteBookingDTO getBookingInfo(String uuid, String routeId, FlightClass flightClass) {
        log.info("FlightRouteService Start - getting cached info for id: {} and route: {}, for class: {}", uuid, routeId, flightClass);
        FlightRouteBookingDTO flightRouteBookingDTO = new FlightRouteBookingDTO();
        FlightRouteDTO flightRouteDTO = flightRouteRepository.findById(uuid).orElseThrow(() -> new NoSuchElementException("No flight route found"));
        log.info("what object: {}", flightRouteDTO);
        Optional<FlightRoute> flightRouteOpt = findRouteById(flightRouteDTO.getRouteDeparture(), routeId);
        if (flightRouteOpt.isEmpty()) {
        flightRouteOpt = findRouteById(flightRouteDTO.getRouteReturn(), routeId);
        }
        FlightRoute flightRoute = flightRouteOpt.orElseThrow(() -> new NoSuchElementException("Flight route not found"));
        flightRouteBookingDTO.setRoute(flightRoute);
        flightRouteBookingDTO.setPassengers(flightRouteDTO.getPassengers());
        flightRouteBookingDTO.setPrice(flightRoute.getPrices().get(flightClass));
        log.info("found info: {}", flightRouteBookingDTO);
        return flightRouteBookingDTO;
    }

    private void prepareFlights(List<List<Flight>> flights, FlightRouteSearchParams params, List<FlightRoute> list) {
        Airport departureAirport = airportService.findAirportByIataCode(params.getDepartureAirportIata());
        Airport arrivalAirport = airportService.findAirportByIataCode(params.getArrivalAirportIata());
        for (List<Flight> flightPath : flights) {
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
            list.add(route);
        }
    }

    private void validateSearchParameters(FlightRouteSearchParams params) {
//        if (params.getDepartureDate().isBefore(LocalDateTime.now())) {
//            throw new IllegalArgumentException("Unacceptable departure date");
//        }

//        if (params.getFlightType().equals(FlightType.ROUND_TRIP) && (params.getReturnDate() == null || params.getReturnDate().isBefore(params.getDepartureDate()))) {
//            throw new IllegalArgumentException("Unacceptable departure date");
//        }

        if (params.getAdult() == 0) {
            throw new IllegalArgumentException("Must include at least one adult passenger");
        }

        if (params.getAdult() > MAX_ADULT || params.getChild() > MAX_CHILD || params.getInfant() > MAX_INFANT) {
            throw new IllegalArgumentException("Unacceptable number of passengers");
        }

    }

    private Optional<FlightRoute> findRouteById(List<FlightRoute> list, String id) {
        return list.stream().filter(route -> id.equals(route.getId())).findFirst();
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
