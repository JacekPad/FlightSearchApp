package com.flight.TicketBooking.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum StripeEvent {

    PAYMENT_SUCCESS("payment_intent.succeeded"),
    PAYMENT_CANCELED("payment_intent.canceled"),
    PAYMENT_FAILED("payment_intent.failed"),
    SUBSCRIPTION_SUCCESS("subscription.payment_succeeded"),
    SUBSCRIPTION_CANCELED("subscription_schedule.canceled"),
    SUBSCRIPTION_FAILED("subscription.payment_failed"),
    CHARGE_SUCCESS("charge.succeeded"),
    CHARGE_REFUNDED("charge.refunded"),
    CHARGE_FAILED("charge.failed");
    private final String value;
}
