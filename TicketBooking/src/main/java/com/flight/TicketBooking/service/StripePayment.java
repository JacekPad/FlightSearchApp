package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.flight.TicketBooking.model.enums.StripeEvent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.WebhookEndpoint;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class StripePayment implements PaymentService {

    private final String PAYMENT_INTENT = "payment_intent";
    private final String SESSION = "checkout.session";
    private final String SUBSCRIPTION = "subscription";
    private final String CHARGE = "charge";

    @Value("${stripe.secret_key}")
    private String SECRET_KEY;

    private final BookingService bookingService;

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
        CheckoutSession checkoutSession = new CheckoutSession(session.getId(), session.getClientReferenceId());
        log.info("SERVICE - processPayment - created session: {}", checkoutSession);
        return checkoutSession;
    }

    @Override
    public void processWebhook(String json) {
        Event event = ApiResource.GSON.fromJson(json, Event.class);
        log.info("SERVICE - processWebhook - received stripe webhook event: {}", event);
//        TODO do the cancel event too (own controller endpoint from angular
        if (event.getType().contains(SESSION)) {
            processSession(event);
        }
    }

    private void processSession(Event event) {
        if (("checkout.session.completed").equals(event.getType())) {
//            TODO update booking based on the reference id
            Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElseThrow(NoSuchElementException::new);
            String clientReferenceId = sessionEvent.getClientReferenceId();
            log.info("client reference: {}", clientReferenceId);
//            bookingService.updateBookingPaymentStatus(PaymentStatus.SUCCESS);
        }
    }

    private SessionCreateParams prepareSessionParams(CheckoutInfo checkoutInfo) {
        String clientReferenceId = String.valueOf(UUID.randomUUID());
        return SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.BLIK)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.P24)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.PAYPAL)
                .setClientReferenceId(clientReferenceId)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(checkoutInfo.getSuccessUrl() + "/" + clientReferenceId)
                .setCancelUrl(checkoutInfo.getCancelUrl() + "/" + clientReferenceId)
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency(checkoutInfo.getCurrency()).setUnitAmount(Long.parseLong(checkoutInfo.getPrice()))
                                        .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(checkoutInfo.getRouteId()).build()).build()
                        ).build()
                ).build();
    }
}
