package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.Airport;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AirportRepository extends Neo4jRepository<Airport, String> {

    Airport findByIata(String iata);
}
