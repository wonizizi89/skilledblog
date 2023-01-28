package com.example.myblog1.common.security;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true,access= AccessLevel.PROTECTED)
public class SecurityExceptionDto {

    private final int statusCode;
    private final String msg;

    public SecurityExceptionDto(int statusCode,String msg){
        this.statusCode = statusCode;
        this.msg = msg;
    }
}

