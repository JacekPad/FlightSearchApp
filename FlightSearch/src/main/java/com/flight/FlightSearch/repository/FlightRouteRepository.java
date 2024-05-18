package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.DTO.FlightRouteDTO;
import org.springframework.data.repository.CrudRepository;

public interface FlightRouteRepository extends CrudRepository<FlightRouteDTO, String> {

}
