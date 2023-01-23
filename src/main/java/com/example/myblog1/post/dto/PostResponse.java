package com.example.myblog1.post.dto;


import com.example.myblog1.comment.dto.CommentResponse;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.post.entity.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(force = true, access = AccessLevel.PROTECTED)
public class PostResponse {

    private final Long id;
    private final String title;
    private final String username;
    private final String content;
    private final List<CommentResponse> comments;



    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.content = post.getContent();
        this.comments = post.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }




}
