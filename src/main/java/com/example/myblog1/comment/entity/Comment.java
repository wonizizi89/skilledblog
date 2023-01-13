package com.example.myblog1.comment.entity;

import com.example.myblog1.common.Timestamped;
import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
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
        this.content = commentRequest.getComment();
        this.user = user;
        this.posts = posts;
    }
    public void updateComment(CommentRequest commentRequest) {
        this.content = commentRequest.getComment();
    }
}
