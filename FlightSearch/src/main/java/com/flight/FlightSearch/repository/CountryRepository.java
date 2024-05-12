package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.CountryEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CountryRepository extends Neo4jRepository<CountryEntity, String> {
}
