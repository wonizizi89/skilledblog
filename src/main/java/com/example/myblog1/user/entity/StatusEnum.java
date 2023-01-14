package com.example.myblog1.user.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum StatusEnum {
    SIGNUP_SUCCESS(200,"회원가입 성공"),
    LOGIN_SUCCESS(200, "로그인 성공"),

    POSTS_DELETE_SUCCESS(200,"게시글 삭제 성공"),
    USER_DELETE_SUCCESS(200,"회원 탈퇴 성공" );





    int statusCode;
    String msg;
    StatusEnum(int statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }

}
