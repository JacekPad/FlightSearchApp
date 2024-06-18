package com.flight.TicketBooking.repository;

import com.flight.TicketBooking.model.entity.FlightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<FlightEntity, String> {
}
