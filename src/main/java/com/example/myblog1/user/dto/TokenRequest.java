package com.example.myblog1.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Getter
public class TokenRequest {
        private String accessToken;
        private String refreshToken;
    }


