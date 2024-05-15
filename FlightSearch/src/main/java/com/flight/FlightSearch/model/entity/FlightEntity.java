package com.flight.FlightSearch.model.entity;

import com.flight.FlightSearch.model.Airline;
import com.flight.FlightSearch.model.Airport;
import com.flight.FlightSearch.model.FlightOption;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;

@Node("Flight")
@Data
public class FlightEntity {

    @Id
    @GeneratedValue
    String flightId;

    @Relationship(value = "FROM", direction = Relationship.Direction.OUTGOING)
    Airport from;

    @Relationship(value = "TO", direction = Relationship.Direction.OUTGOING)
    Airport to;

    @Relationship(value = "OPTION", direction = Relationship.Direction.OUTGOING)
    List<FlightOption> options;

    @Relationship(value = "BELONGS_TO", direction = Relationship.Direction.OUTGOING)
    Airline airline;

    LocalDateTime arrivalDate;

    LocalDateTime departureDate;

}
