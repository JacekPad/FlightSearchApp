package com.flight.FlightSearch.repository;

import com.flight.FlightSearch.model.DTO.FlightRouteSearch;
import com.flight.FlightSearch.model.FlightRoute;
import org.springframework.data.repository.CrudRepository;

public interface FlightRouteRepository extends CrudRepository<FlightRouteSearch, String> {

}
