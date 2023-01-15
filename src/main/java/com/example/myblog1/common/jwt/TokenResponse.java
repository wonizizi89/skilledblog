package com.example.myblog1.common.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
