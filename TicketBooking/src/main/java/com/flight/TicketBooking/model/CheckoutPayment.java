package com.flight.TicketBooking.model;

import lombok.Data;

@Data
public class CheckoutPayment {

    private String currency;
    private String price;
    private String successUrl;
    private String cancelUrl;
    private String routeId;
}
