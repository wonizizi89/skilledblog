package com.example.myblog1.entity;

import javax.persistence.*;

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false)
    private String content;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name= "user-id")
    private User user;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="user-id")
    private Posts Posts;

    public Comment(String content, User user, com.example.myblog1.entity.Posts posts) {
        this.content = content;
        this.user = user;
        Posts = posts;
    }
}
