package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.AirlineEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AirlineRepository extends Neo4jRepository<AirlineEntity, String> {

    @Query("MATCH (flight:Flight)-[:BELONGS_TO]->(airline:Airline) where elementId(flight)=$flightId return airline")
    AirlineEntity findAirlineByFlightId(String flightId);
}
