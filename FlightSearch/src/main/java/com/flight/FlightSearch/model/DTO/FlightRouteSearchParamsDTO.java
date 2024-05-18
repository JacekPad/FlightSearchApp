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
    @Size(min = 3, max = 3, message = "Iata code must be 3 characters long")
    private String departureAirportIata;

    @NotNull
    @Size(min = 3, max = 3, message = "Iata code must be 3 characters long")
    private String arrivalAirportIata;

    @NotNull
    @DateValidatorConstraint
    private String departureDate;

    @Null
    @DateValidatorConstraint
    private String returnDate;

    @NotNull
    @Size(min = 5, max = 6, message = "Flight type must be between 5 and 6 characters")
    private FlightType flightType;

    @NotNull
    @Max(value = 5, message = "Maximum number of stops is 5")
    @Min(value = 1, message = "Number of stops must be at least 1")
    private int maxStops;

    @NotNull
    @Max(value = 5, message = "Maximum number of adults is 5")
    @Min(value = 1, message = "At least one adult is required")
    private int adult;

    @NotNull
    @Max(value = 5, message = "Maximum children of adults is 5")
    @Min(value = 0, message = "Value can not be negative")
    private int child;

    @NotNull
    @Max(value = 5, message = "Maximum number of infants is 5")
    @Min(value = 0, message = "Value can not be negative")
    private int infant;
}
