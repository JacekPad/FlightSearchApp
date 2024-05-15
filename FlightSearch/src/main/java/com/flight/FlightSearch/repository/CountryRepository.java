package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.Country;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CountryRepository extends Neo4jRepository<Country, String> {
}
