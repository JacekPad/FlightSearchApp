package com.flight.FlightSearch.utils.mappers;

import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.enums.FlightClass;

import java.util.ArrayList;
import java.util.HashMap;

public class FlightMapper {


    public static Flight toFlight(FlightEntity entity) {
        Flight flight = new Flight();
        flight.setFlightId(entity.getFlightId());
        flight.setFrom(entity.getFrom());
        flight.setTo(entity.getTo());
        flight.setAirline(entity.getAirline());
        flight.setArrivalDate(entity.getArrivalDate());
        flight.setDepartureDate(entity.getDepartureDate());
        HashMap<FlightClass, FlightOption> map = new HashMap<>();
        entity.getOptions().forEach(option -> {
            map.put(option.getFlightClass(), option);
        });
        flight.setOptions(map);
        return flight;
    }
    public static FlightEntity toFlightEntity(Flight flight) {
        FlightEntity flightEntity = new FlightEntity();
        flightEntity.setFrom(flight.getFrom());
        flightEntity.setTo(flight.getTo());
        flightEntity.setAirline(flight.getAirline());
        flightEntity.setDepartureDate(flight.getDepartureDate());
        flightEntity.setArrivalDate(flight.getArrivalDate());
        flightEntity.setOptions(new ArrayList<>(flight.getOptions().values()));
        return flightEntity;
    }
}
