package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.model.enums.PaymentStatus;

public interface BookingService {

    BookingEntity saveBookingData(BookingEntity bookingEntity);

    void updateBookingPaymentStatus(String clientReferenceId, PaymentStatus status);
}
