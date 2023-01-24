package com.example.myblog1.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenRequest {
        private String accessToken;
        private String refreshToken;
    }


