package com.example.myblog1.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
//@Getter 와 @NoArgsConstructor 로 값을 set 할 수 있음
public class SignupRequest {

    @NotBlank
    @Size(min=4,max=10)
    @Pattern(regexp = "^[a-z0-9]*$", message =" a-z , 0-9 만 입력하세요.")
    private  String username;

    @NotBlank
    @Size(min=8,max=15)
    @Pattern(regexp = "^[A-Za-z0-9]*$", message =" A-Z,a-z , 0-9 만 입력하세요.")
    private  String password;

    @Email
    @NotBlank
    private  String email;


    private boolean admin = false;
    private String adminToken="";
    @Builder
    public SignupRequest(String username, String password, String email, boolean admin, String adminToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.admin = admin;
        this.adminToken = adminToken;
    }
}
