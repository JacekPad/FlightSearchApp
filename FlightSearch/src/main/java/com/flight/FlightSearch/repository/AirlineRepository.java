package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.Airline;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AirlineRepository extends Neo4jRepository<Airline, String> {

    @Query("MATCH (flight:Flight)-[:BELONGS_TO]->(airline:Airline) where elementId(flight)=$flightId return airline")
    Airline findAirlineByFlightId(String flightId);

    Airline findByIata(String iata);
}
