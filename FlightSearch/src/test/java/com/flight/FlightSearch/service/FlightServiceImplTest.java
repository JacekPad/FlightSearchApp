package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.FlightType;
import com.flight.FlightSearch.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

class FlightServiceImplTest {

    @Mock
    FlightRepository flightRepository;

    @Mock
    FlightOptionService flightOptionService;

    @Mock
    AirportService airportService;

    @Mock
    AirlineService airlineService;

    @InjectMocks
    FlightServiceImpl flightService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private List<List<Flight>> mockFlights(Flight... flights) {
        List<Flight> flightList = new ArrayList<>(List.of(flights));
        return new ArrayList<>(List.of(flightList));
    }

    private FlightEntity createFlight(Airport from, Airport to, LocalDateTime departure, LocalDateTime arrival) {
        FlightEntity flight = new FlightEntity();
        flight.setFlightId(String.valueOf(UUID.randomUUID()));
        flight.setFrom(from);
        flight.setTo(to);
        flight.setAirline(null);
        flight.setDepartureDate(departure);
        flight.setArrivalDate(arrival);
        return flight;
    }

    private FlightRouteSearchParams getParams() {
        FlightRouteSearchParams params = new FlightRouteSearchParams();
        params.setAdult(1);
        params.setChild(1);
        params.setInfant(1);
        params.setFlightClass(FlightClass.ECONOMY);
        params.setMaxStops(4);
        params.setDepartureAirportIata("AAA");
        params.setArrivalAirportIata("BBB");
        params.setDepartureDate(LocalDateTime.now());
        params.setFlightType(FlightType.ONEWAY);
        return params;
    }

    private Airport getAirport(String iata) {
        Airport airport = new Airport();
        airport.setId(String.valueOf(UUID.randomUUID()));
        airport.setIata(iata);
        airport.setName("Test");
        return airport;
    }

    private FlightOption getOption(FlightClass flightClass, Integer price, Integer seats) {
        FlightOption option = new FlightOption();
        option.setFlightClass(flightClass);
        option.setPrice(price);
        option.setSeats(seats);
        option.setId(String.valueOf(UUID.randomUUID()));
        return option;
    }

    @Test
    void findFlights_whenNoPlanesForDate_shouldNotAddToList() {
        FlightRouteSearchParams params = getParams();
        params.setDepartureDate(LocalDateTime.now().minusDays(2));
        FlightOption option = getOption(FlightClass.ECONOMY, 1, 99);
        FlightEntity flight = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().minusDays(2), params.getDepartureDate().plusDays(7));
        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));
        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight));
        when(flightOptionService.getFlightOptionsByFlightId(any())).thenReturn(List.of(option));
        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.isEmpty();
    }

    @Test
    void findFlights_whenTooFewEmptySeats_shouldNotAddToList() {
        FlightRouteSearchParams params = getParams();
        FlightOption option = getOption(FlightClass.ECONOMY, 1, 0);
        FlightEntity flight = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));
        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight));
        when(flightOptionService.getFlightOptionsByFlightId(any())).thenReturn(List.of(option));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.isEmpty();
    }

    @Test
    void findFlights_whenInfant_shouldNotAddToRequiredSeats() {
        FlightRouteSearchParams params = getParams();
        FlightOption option1 = getOption(FlightClass.ECONOMY, 1, 3);
        FlightEntity flight1 = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));

        FlightEntity flight2 = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));
        FlightOption option2 = getOption(FlightClass.ECONOMY, 1, 2);

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));
        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight1, flight2));
        when(flightOptionService.getFlightOptionsByFlightId(flight1.getFlightId())).thenReturn(List.of(option1));
        when(flightOptionService.getFlightOptionsByFlightId(flight2.getFlightId())).thenReturn(List.of(option2));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.size() == 2;
    }

    @Test
    void findFlights_whenWrongClassFlight_shouldNotAddToList() {
        FlightRouteSearchParams params = getParams();
        FlightOption option = getOption(FlightClass.FIRST, 1, 99);
        FlightEntity flight = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));
        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight));
        when(flightOptionService.getFlightOptionsByFlightId(any())).thenReturn(List.of(option));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.isEmpty();
    }

    @Test
    void findFlights_whenValid_shouldAddToList() {
        FlightRouteSearchParams params = getParams();
        FlightOption option = getOption(FlightClass.ECONOMY, 1, 99);
        FlightEntity flight = createFlight(getAirport("AAA"), getAirport("BBB"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));
        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight));
        when(flightOptionService.getFlightOptionsByFlightId(any())).thenReturn(List.of(option));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.size() == 1;
        assert flights.get(0).size() == 1;
    }

    @Test
    void findFlights_whenMultiStopFlight_shouldAddALlToList() {

        FlightRouteSearchParams params = getParams();
        FlightOption option = getOption(FlightClass.ECONOMY, 1, 99);
        FlightEntity flight1 = createFlight(getAirport("AAA"), getAirport("AA"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));
        FlightEntity flight2 = createFlight(getAirport("AA"), getAirport("BBB"), flight1.getArrivalDate().plusDays(1), params.getDepartureDate().plusDays(7));

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));

        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight1));
        when(flightRepository.findDepartureAirportConnections("AA")).thenReturn(List.of(flight2));

        when(flightOptionService.getFlightOptionsByFlightId(flight1.getFlightId())).thenReturn(List.of(option));
        when(flightOptionService.getFlightOptionsByFlightId(flight2.getFlightId())).thenReturn(List.of(option));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.size() == 1;
        assert flights.get(0).size() == 2;
    }


    @Test
    void findFlights_whenFlightsLooped_shouldNotAddToList() {

        FlightRouteSearchParams params = getParams();
        FlightOption option = getOption(FlightClass.ECONOMY, 1, 99);
        FlightEntity flight1 = createFlight(getAirport("AAA"), getAirport("AA"), params.getDepartureDate().plusDays(2), params.getDepartureDate().plusDays(7));
        FlightEntity flight2 = createFlight(getAirport("AA"), getAirport("AAA"), flight1.getArrivalDate().plusDays(1), params.getDepartureDate().plusDays(7));

        when(airportService.findAirportByIataCode(params.getDepartureAirportIata())).thenReturn(getAirport(params.getDepartureAirportIata()));

        when(flightRepository.findDepartureAirportConnections(params.getDepartureAirportIata())).thenReturn(List.of(flight1));
        when(flightRepository.findDepartureAirportConnections("AA")).thenReturn(List.of(flight2));

        when(flightOptionService.getFlightOptionsByFlightId(flight1.getFlightId())).thenReturn(List.of(option));
        when(flightOptionService.getFlightOptionsByFlightId(flight2.getFlightId())).thenReturn(List.of(option));

        List<List<Flight>> flights = flightService.findFlights(params);
        assert flights.isEmpty();
    }
}