package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.model.enums.PaymentStatus;

public interface PaymentService {

    CheckoutSession processPayment(CheckoutInfo checkoutInfo);
    boolean updatePaymentStatus(String bookingId, PaymentStatus status);

}
