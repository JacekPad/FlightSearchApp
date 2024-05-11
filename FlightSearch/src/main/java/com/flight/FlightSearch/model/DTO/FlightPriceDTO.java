package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class FlightPriceDTO {

    FlightClass flightClass;
    BigDecimal price;

}
