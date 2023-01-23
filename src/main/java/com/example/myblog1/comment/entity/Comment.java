package com.example.myblog1.comment.entity;

import com.example.myblog1.common.Timestamped;
import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.post.entity.Post;
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
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID", nullable = false)
    private Post post;


    public Comment(CommentRequest commentRequest, User user, Post post) {
        this.comment = commentRequest.getComment();
        this.user = user;
        this.post = post;
    }
    public void updateComment(CommentRequest commentRequest) {
        this.comment = commentRequest.getComment();
    }
}
