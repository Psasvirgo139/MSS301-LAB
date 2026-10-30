package fu.de190010.roomservice.dto;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class ApiResponseDTO {
    private int status;
    private String message;
    private Object data;
    private String timestamp;

    public ApiResponseDTO() {
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    public ApiResponseDTO(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now());
    }

    public static ApiResponseDTO ok(String message, Object data) {
        return new ApiResponseDTO(1, message, data);
    }

    public static ApiResponseDTO error(int status, String message) {
        return new ApiResponseDTO(status, message, null);
    }

    public static ApiResponseDTO of(int status, String message, Object data) {
        return new ApiResponseDTO(status, message, data);
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}
