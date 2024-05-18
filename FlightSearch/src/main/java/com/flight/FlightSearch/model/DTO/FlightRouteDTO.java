package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.FlightRoute;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@RedisHash(value = "routes", timeToLive = 600)
public class FlightRouteDTO {

    @Id
    String id;
    List<FlightRoute> routeDeparture;
    List<FlightRoute> routeReturn;
    List<PassengerDTO> passengers;
}
