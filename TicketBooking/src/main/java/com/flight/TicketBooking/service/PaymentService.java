package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;

public interface PaymentService {

    CheckoutSession processPayment(CheckoutInfo checkoutInfo);
}
