package com.example.myblog1.common.jwt;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
