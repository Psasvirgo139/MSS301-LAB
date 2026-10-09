package com.fudn.movie_service.service;

import com.fudn.movie_service.dto.RoomRequest;
import com.fudn.movie_service.dto.RoomResponse;
import com.fudn.movie_service.exception.ApiException;
import com.fudn.movie_service.model.CinemaRoom;
import com.fudn.movie_service.repository.RoomRepository;
import com.fudn.movie_service.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final ShowtimeRepository showtimeRepository;

    public List<RoomResponse> getAll() {
        return roomRepository.findAll(Sort.by("roomName")).stream().map(RoomResponse::from).toList();
    }

    public RoomResponse getById(String id) {
        return RoomResponse.from(find(id));
    }

    public RoomResponse create(RoomRequest request) {
        String name = request.getEffectiveName();
        if (roomRepository.existsByRoomNameIgnoreCase(name)) {
            throw ApiException.conflict("Room name already exists: " + name);
        }
        CinemaRoom room = new CinemaRoom();
        apply(room, request);
        return RoomResponse.from(roomRepository.save(room));
    }

    public RoomResponse update(String id, RoomRequest request) {
        CinemaRoom room = find(id);
        String name = request.getEffectiveName();
        if (roomRepository.existsByRoomNameIgnoreCaseAndRoomIdNot(name, id)) {
            throw ApiException.conflict("Room name already exists: " + name);
        }
        apply(room, request);
        return RoomResponse.from(roomRepository.save(room));
    }

    public void delete(String id) {
        CinemaRoom room = find(id);
        if (showtimeRepository.existsByRoomId(id)) {
            throw ApiException.conflict("Cannot delete room that already has showtimes");
        }
        roomRepository.delete(room);
    }

    CinemaRoom find(String id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> ApiException.notFound("Room not found with id: " + id));
    }

    private void apply(CinemaRoom room, RoomRequest request) {
        room.setRoomName(request.getEffectiveName());
        room.setRoomType(request.getEffectiveType());
        room.setSeatRows(request.getEffectiveRows());
        room.setSeatsPerRow(request.getEffectiveCols());
        room.setRoomStatus(request.getEffectiveStatus());
    }
}
