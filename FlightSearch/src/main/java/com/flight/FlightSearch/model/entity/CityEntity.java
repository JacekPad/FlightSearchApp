package com.flight.FlightSearch.model.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("City")
@Data
public class CityEntity {

    @Id
    @GeneratedValue
    String id;

    String code;

    String name;

//    TODO make cities belong to countries
//    CountryEntity country;

}
