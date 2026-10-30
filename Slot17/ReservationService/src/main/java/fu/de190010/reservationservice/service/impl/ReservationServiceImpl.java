package fu.de190010.reservationservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import fu.de190010.reservationservice.client.RoomClient;
import fu.de190010.reservationservice.dto.*;
import fu.de190010.reservationservice.entity.Reservation;
import fu.de190010.reservationservice.entity.ReservationStatus;
import fu.de190010.reservationservice.repository.ReservationRepository;
import fu.de190010.reservationservice.service.ReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomClient roomClient;
    private final ObjectMapper objectMapper;

    public ReservationServiceImpl(ReservationRepository reservationRepository,
                                  RoomClient roomClient,
                                  ObjectMapper objectMapper) {
        this.reservationRepository = reservationRepository;
        this.roomClient = roomClient;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public ApiResponseDTO createReservation(ReservationDTO dto) {
        // Date validation (status 2 -> 406)
        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            return ApiResponseDTO.error(2, "checkOutDate must be after checkInDate");
        }

        // Fetch room from RoomService via OpenFeign
        RoomDTO room = fetchRoom(dto.getRoomId());
        if (room == null) {
            return ApiResponseDTO.error(4, "Room ID is not found."); // 404
        }
        if (!"AVAILABLE".equalsIgnoreCase(room.getStatus())) {
            return ApiResponseDTO.error(5, "Room is not AVAILABLE for reservation."); // 400
        }
        if (dto.getNumberOfGuests() > room.getCapacity()) {
            return ApiResponseDTO.error(2, "numberOfGuests exceeds room capacity."); // 406
        }

        long nights = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
        BigDecimal totalAmount = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Reservation r = new Reservation();
        r.setGuestName(dto.getGuestName());
        r.setGuestEmail(dto.getGuestEmail());
        r.setGuestPhone(dto.getGuestPhone());
        r.setCheckInDate(dto.getCheckInDate());
        r.setCheckOutDate(dto.getCheckOutDate());
        r.setNumberOfGuests(dto.getNumberOfGuests());
        r.setRoomId(dto.getRoomId());
        r.setTotalAmount(totalAmount);
        r.setStatus(ReservationStatus.CONFIRMED); // default CONFIRMED

        Reservation saved = reservationRepository.save(r);
        return ApiResponseDTO.of(1, "Reservation created successfully", toDTO(saved));
    }

    @Override
    @Transactional
    public ApiResponseDTO updateReservation(Long reservationId, ReservationDTO dto) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) {
            return ApiResponseDTO.error(4, "Reservation ID is not found.");
        }

        Long effectiveRoomId = dto.getRoomId() != null ? dto.getRoomId() : r.getRoomId();
        RoomDTO room = fetchRoom(effectiveRoomId);
        if (room == null) {
            return ApiResponseDTO.error(4, "Room ID is not found.");
        }

        LocalDate effectiveCheckIn = dto.getCheckInDate() != null ? dto.getCheckInDate() : r.getCheckInDate();
        LocalDate effectiveCheckOut = dto.getCheckOutDate() != null ? dto.getCheckOutDate() : r.getCheckOutDate();
        if (!effectiveCheckOut.isAfter(effectiveCheckIn)) {
            return ApiResponseDTO.error(2, "checkOutDate must be after checkInDate");
        }

        int effectiveGuests = dto.getNumberOfGuests() != null ? dto.getNumberOfGuests() : r.getNumberOfGuests();
        if (effectiveGuests > room.getCapacity()) {
            return ApiResponseDTO.error(2, "numberOfGuests exceeds room capacity.");
        }

        if (dto.getGuestName() != null)  r.setGuestName(dto.getGuestName());
        if (dto.getGuestEmail() != null) r.setGuestEmail(dto.getGuestEmail());
        if (dto.getGuestPhone() != null) r.setGuestPhone(dto.getGuestPhone());
        r.setCheckInDate(effectiveCheckIn);
        r.setCheckOutDate(effectiveCheckOut);
        r.setNumberOfGuests(effectiveGuests);
        r.setRoomId(effectiveRoomId);
        if (dto.getStatus() != null) {
            try {
                r.setStatus(ReservationStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException ex) {
                return ApiResponseDTO.error(2, "Invalid reservation status");
            }
        }

        long nights = ChronoUnit.DAYS.between(effectiveCheckIn, effectiveCheckOut);
        r.setTotalAmount(room.getPricePerNight().multiply(BigDecimal.valueOf(nights)));

        Reservation saved = reservationRepository.save(r);
        return ApiResponseDTO.ok("Reservation updated successfully", toDTO(saved));
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO getReservationById(Long reservationId) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) {
            return ApiResponseDTO.error(4, "Reservation ID is not found.");
        }
        return ApiResponseDTO.ok("Reservation retrieved successfully", toDetailDTO(r));
    }

    @Override
    @Transactional
    public ApiResponseDTO cancelReservation(Long reservationId) {
        Reservation r = reservationRepository.findById(reservationId).orElse(null);
        if (r == null) {
            return ApiResponseDTO.error(4, "Reservation ID is not found.");
        }
        r.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(r);
        return ApiResponseDTO.ok("Reservation cancelled successfully", null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponseDTO getReservationList(String guestName, String status, Pageable pageable) {
        Page<Reservation> page = reservationRepository.search(emptyToNull(guestName), parseReservationStatus(status), pageable);
        List<ReservationDetailDTO> content = page.getContent().stream().map(this::toDetailDTO).toList();
        PageDTO pageDTO = new PageDTO(
                page.getSize(), page.getNumber(), page.getTotalPages(),
                page.getTotalElements(), page.isFirst(), page.isLast(), content);
        return ApiResponseDTO.ok("Reservations retrieved successfully", pageDTO);
    }

    // -- helpers --
    private RoomDTO fetchRoom(Long roomId) {
        try {
            ApiResponseDTO res = roomClient.getRoomById(roomId);
            if (res == null || res.getStatus() != 1 || res.getData() == null) {
                return null;
            }
            return objectMapper.convertValue(res.getData(), RoomDTO.class);
        } catch (FeignException.NotFound ex) {
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    private ReservationDTO toDTO(Reservation r) {
        ReservationDTO d = new ReservationDTO();
        d.setReservationId(r.getReservationId());
        d.setGuestName(r.getGuestName());
        d.setGuestEmail(r.getGuestEmail());
        d.setGuestPhone(r.getGuestPhone());
        d.setCheckInDate(r.getCheckInDate());
        d.setCheckOutDate(r.getCheckOutDate());
        d.setNumberOfGuests(r.getNumberOfGuests());
        d.setTotalAmount(r.getTotalAmount());
        d.setStatus(r.getStatus().name());
        d.setRoomId(r.getRoomId());
        return d;
    }

    private ReservationDetailDTO toDetailDTO(Reservation r) {
        ReservationDetailDTO d = new ReservationDetailDTO();
        d.setReservationId(r.getReservationId());
        d.setGuestName(r.getGuestName());
        d.setGuestEmail(r.getGuestEmail());
        d.setGuestPhone(r.getGuestPhone());
        d.setCheckInDate(r.getCheckInDate());
        d.setCheckOutDate(r.getCheckOutDate());
        d.setNumberOfGuests(r.getNumberOfGuests());
        d.setTotalAmount(r.getTotalAmount());
        d.setStatus(r.getStatus().name());
        d.setRoomId(r.getRoomId());
        d.setRoom(fetchRoom(r.getRoomId()));
        return d;
    }

    private String emptyToNull(String s) {
        return (s == null || s.isBlank()) ? null : s;
    }

    private ReservationStatus parseReservationStatus(String s) {
        if (s == null || s.isBlank()) return null;
        try { return ReservationStatus.valueOf(s); } catch (IllegalArgumentException e) { return null; }
    }
}
