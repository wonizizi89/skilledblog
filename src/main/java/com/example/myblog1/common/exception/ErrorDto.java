package com.example.myblog1.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class ErrorDto {
    private final int StatusCode;
    private final String message;

    public ErrorDto(int statusCode, String message) {
        StatusCode = statusCode;
        this.message = message;
    }
}
