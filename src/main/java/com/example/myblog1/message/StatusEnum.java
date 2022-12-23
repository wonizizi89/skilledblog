package com.example.myblog1.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum StatusEnum {
    SIGNUP_SUCCESS(200,"회원가입 성공"),
    LOGIN_SUCCESS(200, "로그인 성공"),
    DELETE_SUCCESS(200, "게시글 삭제 성공");

    int statusCode;
    String code;
    StatusEnum(int statusCode, String code) {
        this.statusCode = statusCode;
        this.code = code;
    }

}
