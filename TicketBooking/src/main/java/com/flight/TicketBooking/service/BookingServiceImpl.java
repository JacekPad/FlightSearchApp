package com.flight.TicketBooking.service;

import com.flight.TicketBooking.model.entity.BookingEntity;
import com.flight.TicketBooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

}
