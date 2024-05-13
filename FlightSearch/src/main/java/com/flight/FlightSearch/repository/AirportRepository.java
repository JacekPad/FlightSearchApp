package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.AirportEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AirportRepository extends Neo4jRepository<AirportEntity, String> {

    AirportEntity findByIata(String iata);
}
