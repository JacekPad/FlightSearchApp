package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.FlightEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface FlightRepository extends Neo4jRepository<FlightEntity, String> {

    @Query("MATCH (fromAirport:Airport {iata:$departureAirportIata})<-[p:FROM]-(flight:Flight)-[k:TO]->(toAirport:Airport) " +
            "return fromAirport,p,flight,k,toAirport")
    List<FlightEntity> findDepartureAirportConnections(String departureAirportIata);
}
