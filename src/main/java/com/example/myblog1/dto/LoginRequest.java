package com.example.myblog1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor //세터 대신 DTO 생성자를 생성 하는 방법
public class LoginRequest {

    private String username;
    private String password;


}
