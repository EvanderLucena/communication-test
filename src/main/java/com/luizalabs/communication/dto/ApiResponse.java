package com.luizalabs.communication.dto;

import java.time.LocalDateTime;

public record ApiResponse<T>(
        LocalDateTime timestamp,
        int status,
        String message,
        T data
) {
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(LocalDateTime.now(), 200, message, data);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(LocalDateTime.now(), 200, "Sucesso", data);
    }

    public static <T> ApiResponse<T> error(int status, String message) {
        return new ApiResponse<>(LocalDateTime.now(), status, message, null);
    }
}
