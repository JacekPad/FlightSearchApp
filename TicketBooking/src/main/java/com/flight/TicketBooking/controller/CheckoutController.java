package com.flight.TicketBooking.controller;

import com.flight.TicketBooking.model.CheckoutPayment;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/booking")
public class CheckoutController {

    @Value("${stripe.public_key}")
    private String PUBLIC_KEY;

    @Value("${stripe.secret_key}")
    private String SECRET_KEY;

    @PostMapping("/checkout")
    public Map<String ,String> checkOut(@RequestBody CheckoutPayment payment) throws StripeException {
        log.info("creating transaction session for payment: {}", payment);
        Stripe.apiKey = SECRET_KEY;
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(payment.getCurrency()).setUnitAmount(Long.parseLong(payment.getPrice()))
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(payment.getRouteId()).build()).build()
                        ).build()
                ).build();
        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return responseData;
    }
}
