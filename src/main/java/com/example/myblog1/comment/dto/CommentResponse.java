package com.example.myblog1.comment.dto;

import com.example.myblog1.comment.entity.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true,access = AccessLevel.PROTECTED)
public class CommentResponse {
    private final Long id;
    private final String username;
    private final String comment;

    public CommentResponse(Comment comment) {
        this.id =comment.getId();
        this.username = comment.getUser().getUsername();
        this.comment = comment.getComment();
    }


}
