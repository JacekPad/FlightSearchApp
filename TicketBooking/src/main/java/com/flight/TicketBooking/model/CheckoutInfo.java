package com.flight.TicketBooking.model;

import lombok.Data;

@Data
public class CheckoutInfo {

    private String currency;
    private String price;
    private String successUrl;
    private String cancelUrl;
//    TODO probably something else as name
    private String routeId;
}
