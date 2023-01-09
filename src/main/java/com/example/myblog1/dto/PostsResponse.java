package com.example.myblog1.dto;


import com.example.myblog1.entity.Posts;
import com.example.myblog1.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsResponse {

    private Long id;
    private String title;
    private String username;
    private String content;


    public PostsResponse(Posts posts) {
        this.id = posts.getId();
        this.title = posts.getTitle();
        this.username = posts.getUser().getUsername();
        this.content = posts.getContent();
    }



}
