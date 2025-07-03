package ir.ums.exception;

import java.time.LocalDateTime;

public record ErrorResponseDTO(Integer status, String message, LocalDateTime timestamp) {
    public ErrorResponseDTO(Integer status, String message) {
        this(status, message, LocalDateTime.now());
    }
}
