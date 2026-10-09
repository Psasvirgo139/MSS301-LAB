package com.fudn.booking_service.dto;

import com.fudn.booking_service.exception.ApiException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public record CreateBookingRequest(
        List<BookingItemRequest> items,
        String showtimeId,
        List<String> seatNumbers,
        String seatNumber,
        String seatCode,
        BigDecimal totalAmount,
        BigDecimal amount,
        BigDecimal price) {

    public List<BookingItemRequest> getEffectiveItems() {
        if (items != null && !items.isEmpty()) {
            return items;
        }
        String stId = showtimeId;
        if (stId == null || stId.isBlank()) {
            throw ApiException.badRequest("Showtime id is required");
        }
        List<String> seats = new ArrayList<>();
        if (seatNumbers != null && !seatNumbers.isEmpty()) {
            seats.addAll(seatNumbers);
        } else if (seatNumber != null && !seatNumber.isBlank()) {
            seats.add(seatNumber);
        } else if (seatCode != null && !seatCode.isBlank()) {
            seats.add(seatCode);
        }

        if (seats.isEmpty()) {
            throw ApiException.badRequest("Booking must have at least 1 ticket");
        }
        if (seats.size() > 8) {
            throw ApiException.badRequest("A booking can have at most 8 tickets");
        }

        List<BookingItemRequest> result = new ArrayList<>();
        for (String s : seats) {
            result.add(new BookingItemRequest(stId, s));
        }
        return result;
    }
}

