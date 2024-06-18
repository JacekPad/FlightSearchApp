package com.flight.TicketBooking.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Entity
@Table(name = "b_booking")
public class BookingEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_session_id")
    private String paymentId;

    @Column(name = "email")
    private String email;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "booking_id")
    private List<PassengerEntity> passengers;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

//    TODO get method from transaction after customer chooses?
    private String payMethod;

    @Column(name = "flights")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "b_bookings_to_flights",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "flight_id")
    )
    private List<FlightEntity> flights;

    @Column(name = "created", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created;

    @PrePersist
    private void setCreateDate() {
        created = LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
}
