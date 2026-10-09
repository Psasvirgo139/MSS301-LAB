package com.fudn.booking_service.dto;

public record SeatResponse(
        String id,
        String seatNumber,
        String name,
        String row,
        int col,
        String status,
        boolean isBooked) {
}
