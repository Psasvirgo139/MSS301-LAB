package com.fudn.movie_service.dto;

import com.fudn.movie_service.model.CinemaRoom;
import com.fudn.movie_service.model.Movie;
import com.fudn.movie_service.model.Showtime;
import com.fudn.movie_service.model.ShowtimeStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShowtimeResponse(
        String id,
        String showtimeId,
        String movieId,
        String movieTitle,
        String roomId,
        String roomName,
        int seatRows,
        int seatsPerRow,
        LocalDateTime startTime,
        LocalDateTime endTime,
        BigDecimal price,
        BigDecimal ticketPrice,
        ShowtimeStatus showtimeStatus,
        ShowtimeStatus status) {

    public static ShowtimeResponse from(Showtime s, Movie movie, CinemaRoom room) {
        String mTitle = movie != null ? movie.getTitle() : "";
        String rName = room != null ? room.getRoomName() : "";
        int rRows = room != null ? room.getSeatRows() : 0;
        int rCols = room != null ? room.getSeatsPerRow() : 0;
        return new ShowtimeResponse(
                s.getShowtimeId(),
                s.getShowtimeId(),
                s.getMovieId(),
                mTitle,
                s.getRoomId(),
                rName,
                rRows,
                rCols,
                s.getStartTime(),
                s.getEndTime(),
                s.getTicketPrice(),
                s.getTicketPrice(),
                s.getShowtimeStatus(),
                s.getShowtimeStatus()
        );
    }
}

