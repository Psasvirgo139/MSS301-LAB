package com.fudn.booking_service.controller;

import com.fudn.booking_service.dto.ReportResponse;
import com.fudn.booking_service.exception.ApiException;
import com.fudn.booking_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final BookingService bookingService;

    @GetMapping("/revenue")
    public ReportResponse getRevenueReport(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw ApiException.badRequest("startDate and endDate query parameters are required");
        }
        return bookingService.report(startDate, endDate);
    }

    @GetMapping("/revenue/by-movie")
    public ResponseEntity<?> getRevenueByMovie(@RequestParam(required = false) String movieId) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/top-movies")
    public ResponseEntity<?> getTopMovies(@RequestParam(required = false, defaultValue = "5") int limit) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/occupancy")
    public ResponseEntity<?> getOccupancyRate(@RequestParam(required = false) String roomId) {
        return ResponseEntity.ok(Map.of("occupancyRate", 0.0, "roomId", roomId != null ? roomId : ""));
    }

    @GetMapping("/daily-sales")
    public ResponseEntity<?> getDailySales(@RequestParam(required = false) String date) {
        return ResponseEntity.ok(Map.of("date", date != null ? date : "", "totalSales", 0));
    }

    @GetMapping("/customer-summary")
    public ResponseEntity<?> getCustomerSummary() {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/revenue/export")
    public ResponseEntity<?> exportRevenue(@RequestParam(required = false) String format) {
        return ResponseEntity.ok(Map.of("status", "SUCCESS", "format", format != null ? format : "json"));
    }

    @GetMapping("/metrics")
    public ResponseEntity<?> getMetrics() {
        return ResponseEntity.ok(Map.of("totalBookings", 0, "activeUsers", 0));
    }

    @GetMapping("/staff-sales")
    public ResponseEntity<?> getStaffSales() {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
