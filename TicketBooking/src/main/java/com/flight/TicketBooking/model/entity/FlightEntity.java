package com.flight.TicketBooking.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "b_flights")
public class FlightEntity {

    @Id
    @Column(name = "flight_id")
    private String id;

    @ManyToMany(mappedBy = "flights")
    List<BookingEntity> bookingEntity;
}
