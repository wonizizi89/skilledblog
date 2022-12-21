package com.example.myblog1.dto;

import com.example.myblog1.message.StatusEnum;

public class ResponseDelete {
    private String code;
    private int statusCode;

    public ResponseDelete(StatusEnum status){
        this.code = status.getCode();
        this.statusCode = status.getStatusCode();
    }


}
