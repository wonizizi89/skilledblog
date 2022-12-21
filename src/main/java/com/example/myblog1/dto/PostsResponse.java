package com.example.myblog1.dto;


import com.example.myblog1.entity.Posts;
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
        this.id = posts.getId() ;
        this.title = posts.getTitle();
        this.username = posts.getUser().getUsername();
        this.content = posts.getContent();

    }
    //    public PostsResponse(Long id, String title, String username, String content) {
//        this.id = id;
//        this.title = title;
//        this.username = username;
//        this.content = content;
//
//    }

    public static PostsResponse of(Posts posts) {
        return new PostsResponse(posts);
    }

}
