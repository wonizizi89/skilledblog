package com.example.myblog1.comment.dto;

import com.example.myblog1.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponse {
    private Long id;
    private String username;
    private String content;

    public CommentResponse(Comment comment) {
        this.id =comment.getId();
        this.username = comment.getUser().getUsername();
        this.content = comment.getContent();
    }


}
