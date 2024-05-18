package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.FlightClass;
import com.flight.FlightSearch.model.enums.FlightType;
import com.flight.FlightSearch.utils.validators.DateValidatorConstraint;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRouteSearchParamsDTO {

    @NotNull
    private FlightClass flightClass;

    @NotNull
    @Pattern(regexp = "[A-Z]*", message = "Only capital letters are allowed")
    @Size(min = 3, max = 3, message = "Iata code must be 3 characters long")
    private String departureAirportIata;

    @NotNull
    @Pattern(regexp = "[A-Z]*", message = "Only capital letters are allowed")
    @Size(min = 3, max = 3, message = "Iata code must be 3 characters long")
    private String arrivalAirportIata;

    @NotNull
    @DateValidatorConstraint
    private String departureDate;

    @DateValidatorConstraint
    private String returnDate;

    @NotNull
    private FlightType flightType;

    @NotNull
    @Positive
    private int maxStops;

    @NotNull
    @Positive
    private int adult;

    @NotNull
    @PositiveOrZero
    private int child;

    @NotNull
    @PositiveOrZero
    private int infant;
}
