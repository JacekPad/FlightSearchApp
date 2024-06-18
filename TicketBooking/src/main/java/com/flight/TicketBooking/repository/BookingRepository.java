package com.flight.TicketBooking.repository;

import com.flight.TicketBooking.model.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
}
