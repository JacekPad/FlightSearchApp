package com.flight.FlightSearch.model.DTO;

import lombok.Data;

@Data
public class FlightDTO {

    String flightId;

    CityDTO from;

    CityDTO to;

    String airline;

    String airplane;
}
