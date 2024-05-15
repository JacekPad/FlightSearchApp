package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.City;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CityRepository extends Neo4jRepository<City, String> {
}
