package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<CityEntity, String> {
}
