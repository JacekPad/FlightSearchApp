package com.flight.TicketBooking.model.DTO;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.entity.BookingEntity;

public record CheckoutDTO(BookingEntity bookingEntity, CheckoutInfo checkoutInfo) {

}
