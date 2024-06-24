package com.flight.TicketBooking.repository;

import com.flight.TicketBooking.model.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {

    Optional<BookingEntity> findByClientReferenceId(String clientReferenceId);
}
