package com.flight.TicketBooking.repository;

import com.flight.TicketBooking.model.entity.PassengerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<PassengerEntity, Long> {
}
