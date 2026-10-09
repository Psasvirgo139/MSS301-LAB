package com.fudn.booking_service.controller;

import com.fudn.booking_service.dto.BookingResponse;
import com.fudn.booking_service.dto.CreateBookingRequest;
import com.fudn.booking_service.dto.SeatMapResponse;
import com.fudn.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private static final String USER_ID = "X-User-Id";

    private final BookingService bookingService;

    // TODO 7.6: Public seat map
    @GetMapping("/showtimes/{showtimeId}/seats")
    public SeatMapResponse getSeatMap(@PathVariable String showtimeId) {
        return bookingService.getSeatMap(showtimeId);
    }

    // TODO 7.5: CUSTOMER create booking
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@RequestHeader(USER_ID) Long userId,
                                  @Valid @RequestBody CreateBookingRequest request) {
        return bookingService.create(userId, request);
    }
}
