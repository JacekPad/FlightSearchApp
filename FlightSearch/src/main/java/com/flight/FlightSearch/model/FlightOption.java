package com.flight.FlightSearch.model;

import com.flight.FlightSearch.model.enums.FlightClass;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("FlightOption")
public class FlightOption {

    @Id
    @GeneratedValue
    String id;

    FlightClass flightClass;

    Integer price;

    int seats;
}
