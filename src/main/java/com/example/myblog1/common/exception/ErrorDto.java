package com.example.myblog1.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class ErrorDto {
    private final int StatusCode;
    private final String message;

}
