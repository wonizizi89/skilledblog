package com.example.myblog1.comment.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentRequest {
    @NotNull
    private String comment;

}
