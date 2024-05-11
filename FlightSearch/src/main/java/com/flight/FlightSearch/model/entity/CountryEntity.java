package com.flight.FlightSearch.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Country")
@Data
public class CountryEntity {

    @Id
    @GeneratedValue
    String id;

    String code;

    String name;
}
