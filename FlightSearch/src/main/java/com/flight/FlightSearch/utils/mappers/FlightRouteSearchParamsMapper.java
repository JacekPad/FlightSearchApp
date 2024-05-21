package com.flight.FlightSearch.utils.mappers;

import com.flight.FlightSearch.model.FlightRouteSearchParams;
import com.flight.FlightSearch.model.DTO.FlightRouteSearchParamsDTO;

import java.time.LocalDateTime;

public class FlightRouteSearchParamsMapper {

    public static FlightRouteSearchParams fromDTO (FlightRouteSearchParamsDTO dto) {
        FlightRouteSearchParams params = new FlightRouteSearchParams();
        params.setFlightClass(dto.getFlightClass());
        params.setDepartureAirportIata(dto.getDepartureAirportIata());
        params.setArrivalAirportIata(dto.getArrivalAirportIata());
        params.setDepartureDate(LocalDateTime.parse(dto.getDepartureDate()));
        if (dto.getReturnDate() != null) {
            params.setReturnDate(LocalDateTime.parse(dto.getReturnDate()));
        }
        params.setFlightType(dto.getFlightType());
        params.setMaxStops(dto.getMaxStops());
        params.setAdult(dto.getAdult());
        params.setChild(dto.getChild());
        params.setInfant(dto.getInfant());
        return params;
    }
}
