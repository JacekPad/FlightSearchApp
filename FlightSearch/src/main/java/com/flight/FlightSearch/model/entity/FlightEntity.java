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

    @Relationship(value = "FROM")
    CityEntity from;

    @Relationship(value = "TO", direction = Relationship.Direction.OUTGOING)
    CityEntity to;

    List<FlightPriceEntity> prices;

//     TODO node?
    String airline;

//    TODO node?
    String airplane;

}
