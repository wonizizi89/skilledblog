package com.example.myblog1.dto;

import com.example.myblog1.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String username;
    private String content;

    public CommentResponse(Comment comment) {
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }


}
