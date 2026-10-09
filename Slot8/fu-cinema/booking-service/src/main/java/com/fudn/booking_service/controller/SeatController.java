package com.fudn.booking_service.controller;

import com.fudn.booking_service.dto.SeatResponse;
import com.fudn.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final BookingService bookingService;

    @GetMapping("/showtime/{showtimeId}")
    public List<SeatResponse> getSeatMap(@PathVariable String showtimeId) {
        return bookingService.getSeatList(showtimeId);
    }

    @GetMapping("/showtime/{showtimeId}/available")
    public List<SeatResponse> getAvailableSeats(@PathVariable String showtimeId) {
        return bookingService.getSeatList(showtimeId).stream()
                .filter(s -> !s.isBooked())
                .toList();
    }
}
