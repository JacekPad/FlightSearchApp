package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.entity.AirlineEntity;
import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.entity.FlightOptionEntity;
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
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightOptionRepository flightOptionRepository;
    private final AirportRepository airportRepository;
    private final AirlineRepository airlineRepository;
    private final FlightRouteRepository flightRouteRepository;

    @Override
    public List<FlightRouteDTO> prepareFlightRoutes(FlightRouteSearchParams params) {
        AirportEntity departureAirport = airportRepository.findByIata(params.getDepartureAirportIata());
        AirportEntity arrivalAirport = airportRepository.findByIata(params.getArrivalAirportIata());
        List<FlightRouteDTO> routes = new ArrayList<>();
        List<List<Flight>> flightPaths = findFlights(params);
        for (List<Flight> flightPath : flightPaths) {
            FlightRouteDTO route = new FlightRouteDTO();
            route.setId(UUID.randomUUID().toString());
            route.setFlights(flightPath);
            route.setDepartureAirport(departureAirport);
            route.setDepartureTime(flightPath.get(0).getDepartureDate());
            route.setArrivalAirport(arrivalAirport);
            route.setArrivalTime(flightPath.get(flightPath.size()-1).getArrivalDate());
            route.setSeatsLeft(calculateSeatsLeft(params.getFlightClass(), flightPath));
            route.setDuration(calculateDuration(flightPath));
            route.setStops(flightPath.size());
            route.setPrices(calculatePrice(flightPath));
            FlightRouteDTO save = flightRouteRepository.save(route);
            routes.add(save);
        }
        return routes;
    }

    public FlightRouteDTO getVal(String uuid) {
        FlightRouteDTO flightRouteDTO = flightRouteRepository.findById(uuid).get();
        return flightRouteDTO;
    }

    private Map<FlightClass, Integer> calculatePrice(List<Flight> flights) {
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
                    .mapToInt(FlightOptionEntity::getPrice)
                    .sum();
            prices.put(c,sum);
            }
        }
        return prices;
    }

    private long calculateDuration(List<Flight> flightPath) {
        LocalDateTime departureDate = flightPath.get(0).getDepartureDate();
        LocalDateTime arrivalDate = flightPath.get(flightPath.size() - 1).getArrivalDate();
        return Duration.between(departureDate, arrivalDate).toMinutes();
    }

    private int calculateSeatsLeft(FlightClass flightClass, List<Flight> flightPath) {
        return flightPath.stream()
                .map(Flight::getOptions)
                .map(f -> f.get(flightClass))
                .mapToInt(FlightOptionEntity::getSeats)
                .min().orElseThrow(NoSuchElementException::new);
    }

    private List<List<Flight>> findFlights(FlightRouteSearchParams params) {
        log.info("SERVICE: findFlightRoutes - START");
        LocalDateTime now = LocalDateTime.now();
        List<List<Flight>> flights = new ArrayList<>();
        AirportEntity airport = airportRepository.findByIata(params.getDepartureAirportIata());
        dfs(airport, params.getArrivalAirportIata(), 0, flights, new ArrayList<>(), new HashSet<>(), params.getMaxStops(), params.getNoOfSeats(), params.getFlightClass(), now);
        log.info("SERVICE: findFlightRoutes - END - Flights found: {}", flights);
        return flights;
    }

    private void dfs(AirportEntity currentAirport, String arrivalAirportIata, int depth, List<List<Flight>> flights, List<Flight> currPath, HashSet<String> visited, int maxSteps, int seats, FlightClass flightClass, LocalDateTime minDepartureTime) {
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
            flight.setOptions(optionsByFlightId);
            Flight fLight = toFLight(flight);
            if (validateDepartureDate(flight.getDepartureDate(), minDepartureTime) && validateFlightOption(optionsByFlightId, seats, flightClass)) {
                AirlineEntity airline = airlineRepository.findAirlineByFlightId(flight.getFlightId());
                flight.setAirline(airline);
                currPath.add(fLight);
                dfs(flight.getTo(), arrivalAirportIata, depth, flights, currPath, visited, maxSteps, seats, flightClass, flight.getArrivalDate());
                currPath.remove(fLight);
            }
        }
        visited.remove(currentAirport.getIata());
    }

    private Flight toFLight(FlightEntity entity) {
        Flight flight = new Flight();
        flight.setFlightId(entity.getFlightId());
        flight.setFrom(entity.getFrom());
        flight.setTo(entity.getTo());
        flight.setAirline(entity.getAirline());
        flight.setArrivalDate(entity.getArrivalDate());
        flight.setDepartureDate(entity.getDepartureDate());
        HashMap<FlightClass, FlightOptionEntity> map = new HashMap<>();
        entity.getOptions().stream().forEach(op -> {
            map.put(op.getFlightClass(), op);
        });
        flight.setOptions(map);
        return flight;
    }

    private boolean validateDepartureDate(LocalDateTime departureDate, LocalDateTime minDepartureDate) {
        return departureDate.isAfter(minDepartureDate);
    }

    private boolean validateFlightOption(List<FlightOptionEntity> options, int seats, FlightClass flightClass) {
//        TODO move to options serivce?
        log.info("OPTIONS: {}", options);
        long count = options.stream().filter(flightClasses -> flightClasses.getFlightClass().equals(flightClass)).filter(seatCheck -> seatCheck.getSeats() >= seats).count();
        log.info(String.valueOf(count));
        return count > 0;
    }
}
