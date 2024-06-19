package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripePayment implements PaymentService {

    @Value("${stripe.secret_key}")
    private String SECRET_KEY;
    @Override
    public CheckoutSession processPayment(CheckoutInfo checkoutInfo) {
        log.info("SERVICE - processPayment - process for info: {}", checkoutInfo);
        Stripe.apiKey = SECRET_KEY;
        SessionCreateParams params = prepareSessionParams(checkoutInfo);
        Session session = null;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            log.error("Exception while creating stripe session: {}", e.getMessage());
//            TODO throw some exception
            throw new RuntimeException("todo");
        }
        CheckoutSession checkoutSession = new CheckoutSession(session.getId());
        log.info("SERVICE - processPayment - created session: {}", checkoutSession);
        return checkoutSession;
    }

    @Override
    public boolean updatePaymentStatus(String bookingId, PaymentStatus status) {
        return false;
    }

    private SessionCreateParams prepareSessionParams(CheckoutInfo checkoutInfo) {
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.P24)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PAYPAL)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(checkoutInfo.getSuccessUrl())
                .setCancelUrl(checkoutInfo.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(checkoutInfo.getCurrency()).setUnitAmount(Long.parseLong(checkoutInfo.getPrice()))
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(checkoutInfo.getRouteId()).build()).build()
                        ).build()
                ).build();
    }
}
