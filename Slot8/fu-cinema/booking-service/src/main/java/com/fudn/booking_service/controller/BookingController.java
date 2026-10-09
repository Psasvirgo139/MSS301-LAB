package com.fudn.booking_service.controller;

import com.fudn.booking_service.dto.BookingResponse;
import com.fudn.booking_service.dto.CreateBookingRequest;
import com.fudn.booking_service.dto.ReportResponse;
import com.fudn.booking_service.dto.SeatMapResponse;
import com.fudn.booking_service.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private static final String USER_ID = "X-User-Id";
    private static final String USER_ROLE = "X-User-Role";

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

    // TODO 8.1: CUSTOMER my bookings
    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(@RequestHeader(USER_ID) Long userId) {
        return bookingService.getMyBookings(userId);
    }

    // TODO 8.1: ADMIN all bookings
    @GetMapping
    public List<BookingResponse> getAll() {
        return bookingService.getAll();
    }

    // TODO 9.3: ADMIN report
    @GetMapping("/report")
    public ReportResponse report(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return bookingService.report(startDate, endDate);
    }

    // TODO 8.2: Owner or ADMIN get booking by id
    @GetMapping("/{id}")
    public BookingResponse getById(@PathVariable Long id,
                                   @RequestHeader(USER_ID) Long userId,
                                   @RequestHeader(value = USER_ROLE, required = false) String role) {
        return bookingService.getById(id, userId, role);
    }

    // TODO 8.3: Owner or ADMIN cancel booking
    @PutMapping("/{id}/cancel")
    public BookingResponse cancel(@PathVariable Long id,
                                  @RequestHeader(USER_ID) Long userId,
                                  @RequestHeader(value = USER_ROLE, required = false) String role) {
        return bookingService.cancel(id, userId, role);
    }
}
