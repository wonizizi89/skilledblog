package com.example.myblog1.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@NoArgsConstructor(force = true,access = AccessLevel.PROTECTED) //세터 대신 DTO 생성자를 생성 하는 방법
public class LoginRequest {

    @NotBlank
    @Size(min=4,max=10 )
    @Pattern(regexp = "^[a-z0-9]*$", message =" a-z , 0-9 만 입력하세요.")
    private final String username;

    @NotBlank
    @Size(min=8,max=15)
    @Pattern(regexp = "^[A-Za-z0-9]*$", message =" A-Z,a-z , 0-9 만 입력하세요.")
    private final String password;

    @Builder
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
