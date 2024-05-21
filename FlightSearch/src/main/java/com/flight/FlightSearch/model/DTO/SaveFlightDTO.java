package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.FlightOption;
import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@ToString
public class SaveFlightDTO {

    String flightId;
    String from;
    String to;
    Map<FlightClass, FlightOption> options;
    String airline;
    LocalDateTime arrivalDate;
    LocalDateTime departureDate;
}
