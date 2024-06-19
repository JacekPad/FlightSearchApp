package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.model.enums.PaymentStatus;
import com.flight.TicketBooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Override
    public BookingEntity saveBookingData(BookingEntity bookingEntity) {
        log.info("BookingService - Saving booking info: {}", bookingEntity);
        BookingEntity savedEntity = bookingRepository.save(bookingEntity);
        log.info("BookingService - END");
        return savedEntity;
    }

    @Override
    public void updateBookingPaymentStatus(PaymentStatus status) {
        log.info("BookingService - updating payment status for id: {} to {}", "test", status.toString());
//        TODO some exception and proper ID
        BookingEntity booking = bookingRepository.findById(123l).orElseThrow(RuntimeException::new);
        booking.setPaymentStatus(status);
        bookingRepository.save(booking);
    }

}
