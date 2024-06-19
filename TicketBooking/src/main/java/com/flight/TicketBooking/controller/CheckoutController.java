package com.flight.TicketBooking.controller;

import com.flight.TicketBooking.model.CheckoutInfo;
import com.flight.TicketBooking.model.DTO.CheckoutSession;
import com.flight.TicketBooking.model.DTO.CheckoutDTO;
import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.model.entity.FlightEntity;
import com.flight.TicketBooking.model.entity.PassengerEntity;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.flight.TicketBooking.service.BookingService;
import com.flight.TicketBooking.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String ,String> checkOut(@RequestBody CheckoutInfo payment) throws StripeException {
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

    @PostMapping("/checkoutNew")
    private CheckoutSession processCheckout(@RequestBody CheckoutDTO checkout) {
        log.info("CONTROLLER - processCheckout for: {}", checkout);
        CheckoutSession checkoutSession = paymentService.processPayment(checkout.checkoutInfo());
        checkout.bookingEntity().setPaymentId(checkoutSession.id());
        bookingService.saveBookingData(checkout.bookingEntity());
        log.info("CONTROLLER - processCheckout return session: {}", checkoutSession);
        return checkoutSession;
    }

     @GetMapping("/generate")
    private CheckoutDTO dto() {
         BookingEntity bookingEntity = new BookingEntity();
         bookingEntity.setPaymentId("abcd-test-test");
         bookingEntity.setEmail("test@email.com");
         List<PassengerEntity> passengers = new ArrayList<>();
         PassengerEntity passenger1 = new PassengerEntity();
         passenger1.setName("name1");
         passenger1.setSurname("surname1");
         passenger1.setBookingEntity(bookingEntity);
         PassengerEntity passenger2 = new PassengerEntity();
         passenger2.setName("name2");
         passenger2.setSurname("surname2");
         passenger1.setBookingEntity(bookingEntity);
         PassengerEntity passenger3 = new PassengerEntity();
         passenger3.setName("name3");
         passenger3.setSurname("surname3");
         passenger1.setBookingEntity(bookingEntity);
         PassengerEntity passenger4 = new PassengerEntity();
         passenger4.setName("name4");
         passenger4.setSurname("surname4");
         passenger1.setBookingEntity(bookingEntity);
         passengers.add(passenger1);
         passengers.add(passenger2);
         passengers.add(passenger3);
         passengers.add(passenger4);
         bookingEntity.setPassengers(passengers);
         bookingEntity.setPaymentStatus(PaymentStatus.PENDING);
         bookingEntity.setPayMethod("CARD");
         List<FlightEntity> flights = new ArrayList<>();
         FlightEntity flight1 = new FlightEntity();
         flight1.setId("abcd-1");
         FlightEntity flight2 = new FlightEntity();
         flight2.setId("abcd-2");
         flights.add(flight1);
         flights.add(flight2);
         bookingEntity.setFlights(flights);

         CheckoutInfo checkoutInfo = new CheckoutInfo();
         checkoutInfo.setRouteId("route-test-1");
         checkoutInfo.setCurrency("usd");
         checkoutInfo.setPrice("10000");
         checkoutInfo.setSuccessUrl("http://localhost:4200");
         checkoutInfo.setCancelUrl("http://localhost:4200");
         CheckoutDTO checkoutDTO = new CheckoutDTO(bookingEntity, checkoutInfo);
         return checkoutDTO;
     }
}
