package hr.abysalto.hiring.mid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
    private String timestamp;

    public ErrorResponse(String message, String code) {
        this.message = message;
        this.code = code;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}
