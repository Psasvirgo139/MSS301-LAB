package com.fudn.booking_service.dto;

import com.fudn.booking_service.model.Booking;
import com.fudn.booking_service.model.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(
        Long id,
        Long bookingId,
        LocalDateTime bookingDate,
        Long customerId,
        BigDecimal totalPrice,
        BigDecimal totalAmount,
        BookingStatus bookingStatus,
        BookingStatus status,
        List<BookingDetailResponse> details) {

    public static BookingResponse from(Booking b) {
        return new BookingResponse(
                b.getBookingId(),
                b.getBookingId(),
                b.getBookingDate(),
                b.getCustomerId(),
                b.getTotalPrice(),
                b.getTotalPrice(),
                b.getBookingStatus(),
                b.getBookingStatus(),
                b.getDetails().stream().map(BookingDetailResponse::from).toList()
        );
    }
}

