package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {
}
