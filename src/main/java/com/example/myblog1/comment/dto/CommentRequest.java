package com.example.myblog1.comment.dto;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.user.entity.User;
import com.fasterxml.classmate.AnnotationOverrides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(force = true,access = AccessLevel.PROTECTED)
public class CommentRequest {
    @NotNull
    private final String comment;


    //Dto ->Entity
    public Comment toEntity(User user, Post post){
        return Comment.builder()
                .comment(comment)
                .user(user)
                .post(post)
                .build();

    }



}
