package com.fudn.movie_service.dto;

import com.fudn.movie_service.model.CinemaRoom;
import com.fudn.movie_service.model.RoomStatus;
import com.fudn.movie_service.model.RoomType;

public record RoomResponse(
        String roomId,
        String id,
        String roomName,
        String name,
        RoomType roomType,
        int seatRows,
        int rows,
        int seatsPerRow,
        int cols,
        int totalSeats,
        RoomStatus roomStatus) {

    public static RoomResponse from(CinemaRoom r) {
        if (r == null) return null;
        return new RoomResponse(
                r.getRoomId(),
                r.getRoomId(),
                r.getRoomName(),
                r.getRoomName(),
                r.getRoomType(),
                r.getSeatRows(),
                r.getSeatRows(),
                r.getSeatsPerRow(),
                r.getSeatsPerRow(),
                r.getTotalSeats(),
                r.getRoomStatus());
    }
}
