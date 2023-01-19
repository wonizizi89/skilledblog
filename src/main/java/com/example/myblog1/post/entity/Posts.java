package com.example.myblog1.post.entity;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.common.Timestamped;
import com.example.myblog1.post.dto.PostsRequest;
import com.example.myblog1.user.entity.User;
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
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.REMOVE)
    List<Comment> commentList = new ArrayList<>();

    public Posts(PostsRequest requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
        this.commentList = getCommentList();

    }



    public void updatePosts(PostsRequest requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }


}
