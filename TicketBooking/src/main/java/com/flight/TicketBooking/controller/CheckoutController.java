package com.flight.TicketBooking.controller;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.DTO.CheckoutDTO;
import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.flight.TicketBooking.service.BookingService;
import com.flight.TicketBooking.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/booking")
@RequiredArgsConstructor
public class CheckoutController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @Value("${stripe.secret_key}")
    private String SECRET_KEY;

    @PostMapping("/checkout")
    private CheckoutSession processCheckout(@RequestBody CheckoutDTO checkout) {
        log.info("CONTROLLER - processCheckout for: {}", checkout);
//        TODO move it to a service and prepare angular DTO with user data as well for asving to db
        CheckoutSession checkoutSession = paymentService.processPayment(checkout.checkoutInfo());
        checkout.bookingEntity().setPaymentId(checkoutSession.id());
        checkout.bookingEntity().setClientReferenceId(checkoutSession.clientReferenceId());
        checkout.bookingEntity().setPaymentStatus(PaymentStatus.PENDING);
        bookingService.saveBookingData(checkout.bookingEntity());
        log.info("CONTROLLER - processCheckout return session: {}", checkoutSession);
        return checkoutSession;
    }

    @PostMapping("checkout/webhook")
    private void webhookEndpoint(@RequestBody String json) throws StripeException {
        log.info("checkout webhook received: {}", json);
        paymentService.processWebhook(json);
    }
}
