package com.fudn.booking_service.controller;

import com.fudn.booking_service.dto.BookingResponse;
import com.fudn.booking_service.dto.CreateBookingRequest;
import com.fudn.booking_service.dto.ReportResponse;
import com.fudn.booking_service.dto.SeatMapResponse;
import com.fudn.booking_service.exception.ApiException;
import com.fudn.booking_service.model.BookingStatus;
import com.fudn.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
    public BookingResponse create(@RequestHeader(value = USER_ID, required = false) Long userId,
                                  @RequestBody CreateBookingRequest request) {
        return bookingService.create(userId, request);
    }

    @PostMapping("/staff")
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse staffCreate(@RequestHeader(value = USER_ID, required = false) Long userId,
                                       @RequestBody CreateBookingRequest request) {
        return bookingService.create(userId, request);
    }

    @PostMapping("/calculate-price")
    public ResponseEntity<?> calculatePrice(@RequestBody(required = false) Map<String, Object> body) {
        return ResponseEntity.ok(Map.of("originalPrice", 100000, "discount", 0, "finalPrice", 100000));
    }

    // TODO 8.1: CUSTOMER my bookings
    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(@RequestHeader(value = USER_ID, required = false) Long userId) {
        return bookingService.getMyBookings(userId);
    }

    @GetMapping("/my-bookings")
    public List<BookingResponse> getMyBookingsAlt(@RequestHeader(value = USER_ID, required = false) Long userId) {
        return bookingService.getMyBookings(userId);
    }

    // TODO 8.1: ADMIN all bookings
    @GetMapping
    public List<BookingResponse> getAll(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String transactionRef) {
        BookingStatus bookingStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                bookingStatus = BookingStatus.valueOf(status.toUpperCase());
            } catch (Exception ignored) {}
        }
        return bookingService.search(bookingStatus, transactionRef);
    }

    @GetMapping("/search")
    public List<BookingResponse> search(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String transactionRef) {
        return getAll(status, transactionRef);
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
                                   @RequestHeader(value = USER_ID, required = false) Long userId,
                                   @RequestHeader(value = USER_ROLE, required = false) String role) {
        return bookingService.getById(id, userId, role);
    }

    @PostMapping("/{id}/confirm")
    public BookingResponse confirm(@PathVariable Long id) {
        return bookingService.confirm(id);
    }

    // TODO 8.3: Owner or ADMIN cancel booking
    @PostMapping("/{id}/cancel")
    public BookingResponse cancelPost(@PathVariable Long id,
                                      @RequestHeader(value = USER_ID, required = false) Long userId,
                                      @RequestHeader(value = USER_ROLE, required = false) String role) {
        return bookingService.cancel(id, userId, role);
    }

    @PutMapping("/{id}/cancel")
    public BookingResponse cancelPut(@PathVariable Long id,
                                     @RequestHeader(value = USER_ID, required = false) Long userId,
                                     @RequestHeader(value = USER_ROLE, required = false) String role) {
        return bookingService.cancel(id, userId, role);
    }

    @PostMapping("/{id}/cancel-by-staff")
    public BookingResponse cancelByStaff(@PathVariable Long id) {
        return bookingService.cancel(id, null, "ADMIN");
    }

    @PostMapping("/{id}/refund")
    public BookingResponse refund(@PathVariable Long id) {
        return bookingService.refund(id);
    }
}

