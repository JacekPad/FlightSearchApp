package com.flight.TicketBooking.controller;

import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.DTO.CheckoutDTO;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.flight.TicketBooking.service.BookingService;
import com.flight.TicketBooking.service.PaymentService;
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

    @Value("${stripe.public_key}")
    private String PUBLIC_KEY;

    @Value("${stripe.secret_key}")
    private String SECRET_KEY;

    @PostMapping("/checkout")
    private CheckoutSession processCheckout(@RequestBody CheckoutDTO checkout) {
        log.info("CONTROLLER - processCheckout for: {}", checkout);
//        TODO move it to a service
        CheckoutSession checkoutSession = paymentService.processPayment(checkout.checkoutInfo());
        checkout.bookingEntity().setPaymentId(checkoutSession.id());
        checkout.bookingEntity().setPaymentStatus(PaymentStatus.PENDING);
        bookingService.saveBookingData(checkout.bookingEntity());
        log.info("CONTROLLER - processCheckout return session: {}", checkoutSession);
        return checkoutSession;
    }

    @PostMapping("checkout/success")
    private void confirmPayment() {
//        TODO setup a webhook with confirmation event
        log.info("payment confirmation for ID: {}", "sad");
        bookingService.updateBookingPaymentStatus(PaymentStatus.SUCCESS);
    }

    @PostMapping("checkout/cancel")
    private void cancelPayment() {
        log.info("payment cancellation for ID: {}", "test");
        bookingService.updateBookingPaymentStatus(PaymentStatus.CANCELED);
    }
}
