package fu.de190010.reservationservice.client;

import fu.de190010.reservationservice.dto.ApiResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "room-service", url = "${room.service.url:http://localhost:8081}")
public interface RoomClient {

    @GetMapping("/api/rooms/{roomId}")
    ApiResponseDTO getRoomById(@PathVariable("roomId") Long roomId);
}
