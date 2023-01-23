package com.example.myblog1.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor //블로그방식
//@AllArgsConstructor 팀원방식
public enum ExceptionStatus {

    PASSWORDS_DO_NOT_MATCH(401,"비밀번호가 일치 하지 않습니다."),
    ADMIN_PASSWORDS_DO_NOT_MATCH(401,"관리자 암호가 틀려 등록이 불가능 합니다."),
    INVALID_TOKEN(401,"유효하지 않은 토큰입니다."),
    REFRESH_TOKEN_DO_NOT_MATCH(401,"refreshToken 이 일치 하지 않습니다."),
    AUTHORIZATION_EXCEPTION(403,"접근할 수 있는 권한이 없습니다."),
    POST_IS_EMPTY(404,"해당 게시글이 존재 하지 않습니다."),
    COMMENT_IS_EMPTY(404,"해당 댓글이 존재 하지 않습니다."),
    USER_IS_NOT_EXIST(404,"사용자가 존재 하지 않습니다."),
    REQUEST_IS_EMPTY(404,"요청이 존재하지 않습니다."),
    PAGE_IS_NOT_EXIST(404,"요청하신 페이지 내역이 존재하지 않습니다."),
    USERNAME_IS_EXIST(409,"이미 등록된 username 입니다.");


    private final int statusCode;
    private final String message;


}
