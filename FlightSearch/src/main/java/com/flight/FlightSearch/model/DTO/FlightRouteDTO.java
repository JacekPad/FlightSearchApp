package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.Flight;
import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.FlightEntity;
import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@RedisHash(value = "routes", timeToLive = 60)
public class FlightRouteDTO {

    @Id
    String id;
    List<Flight> flights;
    Map<FlightClass, Integer> prices;
    AirportEntity departureAirport;
    AirportEntity arrivalAirport;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    Long duration;
    Integer stops;
    Integer seatsLeft;
}
