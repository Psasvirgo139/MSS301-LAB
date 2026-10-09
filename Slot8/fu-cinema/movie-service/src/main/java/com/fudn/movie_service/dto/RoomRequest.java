package com.fudn.movie_service.dto;

import com.fudn.movie_service.exception.ApiException;
import com.fudn.movie_service.model.RoomStatus;
import com.fudn.movie_service.model.RoomType;

public record RoomRequest(
        String roomName,
        String name,
        RoomType roomType,
        String type,
        Integer seatRows,
        Integer rows,
        Integer seatsPerRow,
        Integer cols,
        Integer totalSeats,
        RoomStatus roomStatus,
        String status) {

    public String getEffectiveName() {
        if (roomName != null && !roomName.isBlank()) return roomName;
        if (name != null && !name.isBlank()) return name;
        throw ApiException.badRequest("Room name cannot be empty");
    }

    public RoomType getEffectiveType() {
        if (roomType != null) return roomType;
        if (type != null) {
            try {
                return RoomType.valueOf(type.toUpperCase());
            } catch (Exception ignored) {}
        }
        return RoomType.STANDARD;
    }

    public int getEffectiveRows() {
        if (seatRows != null && seatRows >= 1) return seatRows;
        if (rows != null && rows >= 1) return rows;
        if (totalSeats != null && totalSeats > 0) return 6;
        return 5;
    }

    public int getEffectiveCols() {
        if (seatsPerRow != null && seatsPerRow >= 1) return seatsPerRow;
        if (cols != null && cols >= 1) return cols;
        if (totalSeats != null && totalSeats > 0) return Math.min(10, totalSeats);
        return 10;
    }

    public RoomStatus getEffectiveStatus() {
        if (roomStatus != null) return roomStatus;
        if (status != null) {
            try {
                return RoomStatus.valueOf(status.toUpperCase());
            } catch (Exception ignored) {}
        }
        return RoomStatus.ACTIVE;
    }
}
