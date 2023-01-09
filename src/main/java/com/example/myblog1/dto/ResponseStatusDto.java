package com.example.myblog1.dto;

import com.example.myblog1.entity.StatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseStatusDto {
    private String code;
    private int statusCode;

    public ResponseStatusDto(StatusEnum status){
        this.code = status.getCode();
        this.statusCode = status.getStatusCode();
    }


}
