package com.flight.FlightSearch.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node("City")
@Data
public class CityEntity {

    @Id
    @GeneratedValue
    String id;
    String name;

    @Relationship(value = "IS_IN", direction = Relationship.Direction.OUTGOING)
    CountryEntity isIn;

}