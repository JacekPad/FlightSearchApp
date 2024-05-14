package com.flight.FlightSearch.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Flight")
@Data
public class FlightEntity {

    @Id
    @GeneratedValue
    String flightId;

    @Relationship(value = "FROM", direction = Relationship.Direction.OUTGOING)
    AirportEntity from;

    @Relationship(value = "TO", direction = Relationship.Direction.OUTGOING)
    AirportEntity to;

    @Relationship(value = "OPTION", direction = Relationship.Direction.OUTGOING)
    List<FlightOptionEntity> options;

    @Relationship(value = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    AirlineEntity airline;

//    TODO node?
    String airplane;

}
