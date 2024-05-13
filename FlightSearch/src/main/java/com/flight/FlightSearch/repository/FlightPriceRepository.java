package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.FlightPriceEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface FlightPriceRepository extends Neo4jRepository<FlightPriceEntity, String> {
}
