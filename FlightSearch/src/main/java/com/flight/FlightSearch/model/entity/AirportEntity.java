package com.flight.FlightSearch.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Airport")
@Data
public class AirportEntity {

    @Id
    @GeneratedValue
    String id;
    String iata;
    String latitude;
    String longitude;
    String name;
}
