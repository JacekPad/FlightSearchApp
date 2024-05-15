package com.flight.FlightSearch.model.DTO;

import com.flight.FlightSearch.model.FlightRoute;
import com.flight.FlightSearch.model.enums.PassengerEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.Map;

@Data
@RedisHash(value = "routes", timeToLive = 600)
public class FlightRouteSearch {

    @Id
    String id;
    List<FlightRoute> routes;
    List<PassengerDTO> passengers;
}
