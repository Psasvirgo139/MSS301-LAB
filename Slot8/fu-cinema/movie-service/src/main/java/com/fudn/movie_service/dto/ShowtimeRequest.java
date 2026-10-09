package com.fudn.movie_service.dto;

import com.fudn.movie_service.exception.ApiException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowtimeRequest(
        Object movieId,
        Object roomId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal ticketPrice,
        BigDecimal price) {

    public String getEffectiveMovieId() {
        if (movieId != null && !String.valueOf(movieId).isBlank()) return String.valueOf(movieId);
        throw ApiException.badRequest("Movie id is required");
    }

    public String getEffectiveRoomId() {
        if (roomId != null && !String.valueOf(roomId).isBlank()) return String.valueOf(roomId);
        throw ApiException.badRequest("Room id is required");
    }

    public BigDecimal getEffectivePrice() {
        BigDecimal p = ticketPrice != null ? ticketPrice : price;
        if (p == null) throw ApiException.badRequest("Ticket price is required");
        if (p.compareTo(BigDecimal.valueOf(10000)) < 0 || p.compareTo(BigDecimal.valueOf(1000000)) > 0) {
            throw ApiException.badRequest("Ticket price must be between 10,000 and 1,000,000");
        }
        return p;
    }
}

