package com.flight.FlightSearch.service;

import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.DTO.FlightRouteBookingDTO;
import com.flight.FlightSearch.model.DTO.FlightRouteSearch;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParams;
import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.repository.FlightRouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRouteServiceImplTest {


    @Mock
    FlightRouteRepository flightRouteRepository;

    @Mock
    FlightService flightService;

    @Mock
    AirportService airportService;

    @InjectMocks
    FlightRouteServiceImpl flightRouteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
        return params;
    }

    private Airport getAirport(String iata) {
        Airport airport = new Airport();
        airport.setId(String.valueOf(UUID.randomUUID()));
        airport.setIata(iata);
        airport.setName("Test");
        return airport;
    }

    private List<List<Flight>> getFlights(Flight... flights) {
        List<Flight> flightList = new ArrayList<>(List.of(flights));
        return new ArrayList<>(List.of(flightList));
    }

    private Flight getFlight(Airport from, Airport to, LocalDateTime departure, LocalDateTime arrival, Map<FlightClass, FlightOption> options) {
        Flight flight = new Flight();
        flight.setFlightId(String.valueOf(UUID.randomUUID()));
        flight.setFrom(from);
        flight.setTo(to);
        flight.setAirline(null);
        flight.setDepartureDate(departure);
        flight.setArrivalDate(arrival);
        flight.setOptions(options);
        return flight;
    }

    private FlightOption getOptions(int seats, int price, FlightClass flightClass) {
        FlightOption option = new FlightOption();
        option.setId(String.valueOf(UUID.randomUUID()));
        option.setSeats(seats);
        option.setFlightClass(flightClass);
        option.setPrice(price);
        return option;
    }

    @Test
    void prepareFlightRoutes_whenNoDepartureAirport_shouldThrowException() {
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(null);
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(getParams());
            fail();
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("No airport found", e.getMessage());
        }
    }

    @Test
    void prepareFlightRoutes_whenNoArrivalAirport_shouldThrowException() {
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(null);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(getParams());
            fail();
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("No airport found", e.getMessage());
        }
    }

    //    date is later than today (cannot be)
    @Test
    void prepareFlightRoutes_whenDateIsEarlierThanToday_shouldThrowException() {

    }

    //    max passengers of any kind should be excetpion
    @Test
    void prepareFlightRoutes_whenTooManyAdultPassengers_shouldThrowException() {
        FlightRouteSearchParams params = getParams();
        params.setAdult(5);
        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 1);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);
        when(airportService.findAirportByIataCode(any())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(params);
            fail();
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("Unacceptable number of passengers", e.getMessage());
        }
    }

    @Test
    void prepareFlightRoutes_whenTooManyChildPassengers_shouldThrowException() {
        FlightRouteSearchParams params = getParams();
        params.setChild(5);
        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 1);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);
        when(airportService.findAirportByIataCode(any())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(params);
            fail();
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("Unacceptable number of passengers", e.getMessage());
        }
    }

    @Test
    void prepareFlightRoutes_whenTooManyInfantPassengers_shouldThrowException() {
        FlightRouteSearchParams params = getParams();
        params.setInfant(5);
        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 1);
        when(airportService.findAirportByIataCode(any())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(params);
            fail();
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("Unacceptable number of passengers", e.getMessage());
        }
    }

    @Test
    void prepareFlightRoutes_shouldReturnFlights() {
        FlightOption economyClass = getOptions(5, 100, FlightClass.ECONOMY);
        FlightOption firstClass = getOptions(10, 1000, FlightClass.FIRST);
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        map.put(economyClass.getFlightClass(), economyClass);
        map.put(firstClass.getFlightClass(), firstClass);

        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);

        Flight flight1 = getFlight(getAirport("AAA"), getAirport("AA"), LocalDateTime.now(), LocalDateTime.now().plusDays(5), map);
        Flight flight2 = getFlight(getAirport("AA"), getAirport("BB"), LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(10), map);
        Flight flight3 = getFlight(getAirport("BB"), getAirport("BBB"), LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15), map);
        List<List<Flight>> flights = getFlights(flight1, flight2, flight3);

        when(flightService.findFlights(any())).thenReturn(flights);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("BBB"));

        FlightRouteSearch flightRouteSearch = flightRouteService.prepareFlightRoutes(getParams());
        assert flightRouteSearch.getRoutes().size() == 1;
        assert flightRouteSearch.getRoutes().get(0).getFlights().size() == 3;
    }

    @Test
    void prepareFlightRoutes_whenNoAdultPassenger_shouldThrowException() {
        FlightRouteSearchParams params = getParams();
        params.setAdult(0);
        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);
        when(airportService.findAirportByIataCode(any())).thenReturn(getAirport("AAA"));
        try {
            flightRouteService.prepareFlightRoutes(params);
        } catch (Exception e) {
            assertInstanceOf(IllegalArgumentException.class, e);
            assertEquals("Must include at least one adult passenger", e.getMessage());
        }
    }

    //    calculate total price based on all flights of some type (for all)
    @Test
    void prepareFlightRoutes_whenRouteCreated_shouldCalculateTotalPriceForFlights() {
        FlightOption economyClass = getOptions(5, 100, FlightClass.ECONOMY);
        FlightOption firstClass = getOptions(10, 1000, FlightClass.FIRST);
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        map.put(economyClass.getFlightClass(), economyClass);
        map.put(firstClass.getFlightClass(), firstClass);

        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);

        Flight flight1 = getFlight(getAirport("AAA"), getAirport("AA"), LocalDateTime.now(), LocalDateTime.now().plusDays(5), map);
        Flight flight2 = getFlight(getAirport("AA"), getAirport("BB"), LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(10), map);
        Flight flight3 = getFlight(getAirport("BB"), getAirport("BBB"), LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15), map);
        List<List<Flight>> flights = getFlights(flight1, flight2, flight3);

        when(flightService.findFlights(any())).thenReturn(flights);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("BBB"));

        FlightRouteSearch flightRouteSearch = flightRouteService.prepareFlightRoutes(getParams());
        assert flightRouteSearch.getRoutes().size() == 1;
        assert flightRouteSearch.getRoutes().get(0).getPrices().get(FlightClass.FIRST) == 3000;
        assert flightRouteSearch.getRoutes().get(0).getPrices().get(FlightClass.ECONOMY) == 300;
    }

    //    calculate total duration based on all flights
    @Test
    void prepareFlightRoutes_whenRouteCreated_shouldCalculateTotalDurationForFlights() {
        FlightOption economyClass = getOptions(5, 100, FlightClass.ECONOMY);
        FlightOption firstClass = getOptions(10, 1000, FlightClass.FIRST);
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        map.put(economyClass.getFlightClass(), economyClass);
        map.put(firstClass.getFlightClass(), firstClass);

        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);

        Flight flight1 = getFlight(getAirport("AAA"), getAirport("AA"), LocalDateTime.now(), LocalDateTime.now().plusDays(5), map);
        Flight flight2 = getFlight(getAirport("AA"), getAirport("BB"), LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(10), map);
        Flight flight3 = getFlight(getAirport("BB"), getAirport("BBB"), LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15), map);
        List<List<Flight>> flights = getFlights(flight1, flight2, flight3);

        when(flightService.findFlights(any())).thenReturn(flights);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("BBB"));

        FlightRouteSearch flightRouteSearch = flightRouteService.prepareFlightRoutes(getParams());
        assert flightRouteSearch.getRoutes().size() == 1;
        long days_15 = 21600L;
        assert flightRouteSearch.getRoutes().get(0).getDuration() == days_15;
    }


    @Test
    void prepareFlightRoutes_whenRouteCreated_shouldCalculateMinimalSeatsLeftForFlights() {
        FlightOption economyClass = getOptions(5, 100, FlightClass.ECONOMY);
        FlightOption firstClass = getOptions(10, 1000, FlightClass.FIRST);
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        map.put(economyClass.getFlightClass(), economyClass);
        map.put(firstClass.getFlightClass(), firstClass);

        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);

        Flight flight1 = getFlight(getAirport("AAA"), getAirport("AA"), LocalDateTime.now(), LocalDateTime.now().plusDays(5), map);
        Flight flight2 = getFlight(getAirport("AA"), getAirport("BB"), LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(10), map);
        Flight flight3 = getFlight(getAirport("BB"), getAirport("BBB"), LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15), map);
        List<List<Flight>> flights = getFlights(flight1, flight2, flight3);

        when(flightService.findFlights(any())).thenReturn(flights);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("BBB"));

        FlightRouteSearch flightRouteSearch = flightRouteService.prepareFlightRoutes(getParams());
        assert flightRouteSearch.getRoutes().size() == 1;
        assert flightRouteSearch.getRoutes().get(0).getSeatsLeft() == 5;
    }

    @Test
    void prepareFlightRoutes_whenRouteCreated_shouldCalculateNumberOfStops() {
        FlightOption economyClass = getOptions(5, 100, FlightClass.ECONOMY);
        FlightOption firstClass = getOptions(10, 1000, FlightClass.FIRST);
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        map.put(economyClass.getFlightClass(), economyClass);
        map.put(firstClass.getFlightClass(), firstClass);

        ReflectionTestUtils.setField(flightRouteService, "MAX_ADULT", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_CHILD", 99);
        ReflectionTestUtils.setField(flightRouteService, "MAX_INFANT", 99);

        Flight flight1 = getFlight(getAirport("AAA"), getAirport("AA"), LocalDateTime.now(), LocalDateTime.now().plusDays(5), map);
        Flight flight2 = getFlight(getAirport("AA"), getAirport("BB"), LocalDateTime.now().plusDays(6), LocalDateTime.now().plusDays(10), map);
        Flight flight3 = getFlight(getAirport("BB"), getAirport("BBB"), LocalDateTime.now().plusDays(11), LocalDateTime.now().plusDays(15), map);
        List<List<Flight>> flights = getFlights(flight1, flight2, flight3);

        when(flightService.findFlights(any())).thenReturn(flights);
        when(airportService.findAirportByIataCode(getParams().getDepartureAirportIata())).thenReturn(getAirport("AAA"));
        when(airportService.findAirportByIataCode(getParams().getArrivalAirportIata())).thenReturn(getAirport("BBB"));

        FlightRouteSearch flightRouteSearch = flightRouteService.prepareFlightRoutes(getParams());
        assert flightRouteSearch.getRoutes().size() == 1;
        assert flightRouteSearch.getRoutes().get(0).getStops() == 3;
    }

    @Test
    void getBookingInfo_whenNoRouteWithGivenUUID_shouldThrowException() {
        String UUID = String.valueOf(java.util.UUID.randomUUID());
        String routeId = String.valueOf(java.util.UUID.randomUUID());
        FlightClass flightClass = FlightClass.ECONOMY;

        when(flightRouteRepository.findById(UUID)).thenReturn(Optional.empty());
        try {
            flightRouteService.getBookingInfo(UUID, routeId, flightClass);
            fail();
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            assertEquals("No flight route found", e.getMessage());
        }
    }

    //    throw exception when main id is ok, but flight id not found in the list
    @Test
    void getBookingInfo_whenNoFlightWithGivenId_shouldThrowException() {
        Map<FlightClass, FlightOption> map = new HashMap<>();
        FlightOption options = getOptions(2, 55, FlightClass.ECONOMY);
        map.put(FlightClass.ECONOMY, options);
        FlightRouteSearch flightRouteSearch = new FlightRouteSearch();
        FlightRoute flightRoute = new FlightRoute();
        flightRoute.setId(String.valueOf(UUID.randomUUID()));
        flightRoute.setFlights(List.of(getFlight(getAirport("AAA"), getAirport("BBB"), LocalDateTime.now(), LocalDateTime.now().plusDays(3), map)));
        flightRoute.setPrices(Map.of(FlightClass.ECONOMY, 55));
        flightRouteSearch.setRoutes(List.of(flightRoute));
        when(flightRouteRepository.findById(any())).thenReturn(Optional.of(flightRouteSearch));
        try {
            FlightRouteBookingDTO bookingInfo = flightRouteService.getBookingInfo("random", "differentID", FlightClass.ECONOMY);
            fail();
        } catch (Exception e) {
            assertInstanceOf(NoSuchElementException.class, e);
            assertEquals("No flight route found", e.getMessage());
        }
    }

    //    return ok booking info
    @Test
    void getBookingInfo_shouldReturnFlightRouteBooking() {
        Map<FlightClass, FlightOption> map = new HashMap<>();
        FlightOption options = getOptions(2, 55, FlightClass.ECONOMY);
        map.put(FlightClass.ECONOMY, options);
        FlightRouteSearch flightRouteSearch = new FlightRouteSearch();
        FlightRoute flightRoute = new FlightRoute();
        flightRoute.setId(String.valueOf(UUID.randomUUID()));
        flightRoute.setFlights(List.of(getFlight(getAirport("AAA"), getAirport("BBB"), LocalDateTime.now(), LocalDateTime.now().plusDays(3), map)));
        flightRoute.setPrices(Map.of(FlightClass.ECONOMY, 55));
        flightRouteSearch.setRoutes(List.of(flightRoute));
        when(flightRouteRepository.findById(any())).thenReturn(Optional.of(flightRouteSearch));

        FlightRouteBookingDTO bookingInfo = flightRouteService.getBookingInfo("random", flightRoute.getId(), FlightClass.ECONOMY);
        assert bookingInfo.getRoute().getFlights().size()==1;
        assert bookingInfo.getRoute().getPrices().size()==1;
    }
}