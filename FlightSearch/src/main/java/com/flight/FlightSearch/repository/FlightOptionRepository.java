package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.FlightOption;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface FlightOptionRepository extends Neo4jRepository<FlightOption, String> {
    @Query("MATCH (flight:Flight)-[:OPTION]->(option:FlightOption) where elementId(flight)=$flightId return option")
    List<FlightOption> findOptionsByFlightId(String flightId);
}
