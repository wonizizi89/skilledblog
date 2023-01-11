package com.example.myblog1.user.dto;

import com.example.myblog1.user.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseStatusDto {
    private String msg;
    private int statusCode;

    public ResponseStatusDto(StatusEnum status){
        this.msg = status.getMsg();
        this.statusCode = status.getStatusCode();
    }


}
