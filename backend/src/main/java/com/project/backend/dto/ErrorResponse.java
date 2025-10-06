package com.project.backend.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {

}
