package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.FlightEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FlightRepository extends Neo4jRepository<FlightEntity, String> {


}
