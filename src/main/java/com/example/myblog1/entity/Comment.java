package com.example.myblog1.entity;

import com.example.myblog1.dto.CommentRequest;
import com.example.myblog1.dto.CommentResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POSTS_ID", nullable = false)
    private Posts posts;


    public Comment(CommentRequest commentRequest, User user, Posts posts) {
        this.content = commentRequest.getContent();
        this.user = user;
        this.posts = posts;
    }
    public void updateComment(CommentRequest commentRequest) {
        this.content = commentRequest.getContent();
    }
}
