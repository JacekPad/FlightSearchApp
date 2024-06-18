package com.flight.TicketBooking.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.flight.TicketBooking.model.enums.PassengerAge;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "b_passengers")
public class PassengerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    @JsonIgnore
    private BookingEntity bookingEntity;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "age")
    @Enumerated(EnumType.STRING)
    private PassengerAge passengerAge;
}
