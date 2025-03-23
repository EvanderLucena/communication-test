package com.luizalabs.communication.dto;

import java.time.LocalDateTime;

public record ApiResponseDTO<T>(
        LocalDateTime timestamp,
        int status,
        String message,
        T data
) {

    public static <T> ApiResponseDTO<T> success(T data) {
        return new ApiResponseDTO<>(LocalDateTime.now(), 200, "Sucesso", data);
    }

    public static <T> ApiResponseDTO<T> error(int status, String message) {
        return new ApiResponseDTO<>(LocalDateTime.now(), status, message, null);
    }
}
