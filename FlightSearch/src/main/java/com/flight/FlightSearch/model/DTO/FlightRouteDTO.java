package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.entity.AirportEntity;
import com.flight.FlightSearch.model.entity.FlightEntity;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RedisHash(value = "routes", timeToLive = 60)
public class FlightRouteDTO {

    @Id
    String id;
    List<FlightEntity> flights;
    AirportEntity departureAirport;
    AirportEntity arrivalAirport;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    Long duration;
    Integer stops;
    Integer seatsLeft;
}
