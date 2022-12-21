package com.example.myblog1.entity;

import com.example.myblog1.dto.PostsRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Posts extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "USER_ID",nullable = false)
    private User user;

    @OneToMany
    List<Comment> commentList = new ArrayList<>();

    public Posts(PostsRequest requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;

    }



    public void updatePosts(PostsRequest requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }
}
