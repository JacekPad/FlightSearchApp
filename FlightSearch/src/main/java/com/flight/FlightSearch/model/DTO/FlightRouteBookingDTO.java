package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.enums.PassengerEnum;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class FlightRouteBookingDTO {

    private FlightRoute route;
    private List<PassengerDTO> passengers;
    private Integer price;
}
