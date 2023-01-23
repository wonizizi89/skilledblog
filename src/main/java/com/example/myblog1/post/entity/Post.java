package com.example.myblog1.post.entity;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.common.Timestamped;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    List<Comment> comments = new ArrayList<>();

     //todo  requestDto -> .toEntity() 이용하여 결합도 높이고 의존도 낮추기
     //todo Entity -> responseDto
    public Post(PostRequest requestDto, User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.user = user;
    }



    public void updatePosts(PostRequest requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();

    }


}
