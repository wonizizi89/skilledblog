package com.example.myblog1.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequest {
    @NotNull
    private String content;


}
