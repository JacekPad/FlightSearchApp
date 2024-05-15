package com.flight.FlightSearch.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Airline")
@Data
public class Airline {

    @Id
    @GeneratedValue
    String id;
    String iata;
    String name;
    String logo_url;
}
