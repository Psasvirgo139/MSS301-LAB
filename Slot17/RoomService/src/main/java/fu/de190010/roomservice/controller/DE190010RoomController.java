package fu.de190010.roomservice.controller;

import fu.de190010.roomservice.dto.ApiResponseDTO;
import fu.de190010.roomservice.dto.RoomDTO;
import fu.de190010.roomservice.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
public class DE190010RoomController {

    private final RoomService roomService;

    public DE190010RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    // 1. Create Room -> 201/1, 406/2, 226/3, 500/0
    @PostMapping
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody RoomDTO roomDTO, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE) // 406
                    .body(ApiResponseDTO.error(2, firstError(br)));
        }
        return map(roomService.createRoom(roomDTO), HttpStatus.CREATED); // 201 on success
    }

    // 2. Update Room -> 200/1, 406/2, 226/3, 404/4, 500/0
    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable Long roomId, @RequestBody RoomDTO roomDTO, BindingResult br) {
        if (br.hasErrors()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE) // 406
                    .body(ApiResponseDTO.error(2, firstError(br)));
        }
        return map(roomService.updateRoom(roomId, roomDTO), HttpStatus.OK); // 200 on success
    }

    // 3. Get Room Detail -> 200/1, 404/4, 500/0
    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponseDTO> getDetail(@PathVariable Long roomId) {
        return map(roomService.getRoomById(roomId), HttpStatus.OK);
    }

    // 4. Set Room to Maintenance -> 200/1, 404/4, 500/0
    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponseDTO> setMaintenance(@PathVariable Long roomId) {
        return map(roomService.setRoomToMaintenance(roomId), HttpStatus.OK);
    }

    // 5. Get Room List -> 200/1, 406/2, 500/0
    @GetMapping
    public ResponseEntity<ApiResponseDTO> list(@RequestParam(required = false) String roomType,
                                               @RequestParam(required = false) String status,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size) {
        if (size < 1 || size > 100) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE) // 406
                    .body(ApiResponseDTO.error(2, "size must be between 1 and 100"));
        }
        Pageable pageable = PageRequest.of(page, size);
        return map(roomService.getRoomList(roomType, status, pageable), HttpStatus.OK);
    }

    private ResponseEntity<ApiResponseDTO> map(ApiResponseDTO apiResponse, HttpStatus okStatus) {
        int status = apiResponse.getStatus();
        HttpStatus httpStatus;
        switch (status) {
            case 1: // success
                httpStatus = okStatus;
                break;
            case 2: // validation error
                httpStatus = HttpStatus.NOT_ACCEPTABLE; // 406
                break;
            case 3: // duplicated room number
                httpStatus = HttpStatus.IM_USED; // 226
                break;
            case 4: // room not found
                httpStatus = HttpStatus.NOT_FOUND; // 404
                break;
            default: // server error
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 500
                break;
        }
        return ResponseEntity.status(httpStatus).body(apiResponse);
    }

    private String firstError(BindingResult br) {
        return br.getAllErrors().isEmpty() ? "Validation failed" : br.getAllErrors().get(0).getDefaultMessage();
    }
}
