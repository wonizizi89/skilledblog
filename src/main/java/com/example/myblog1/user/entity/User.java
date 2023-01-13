package com.example.myblog1.user.entity;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.post.entity.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable= false)
    @Enumerated(value=EnumType.STRING)
    private UserRoleEnum userRole;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)// Todo :cascade = CascadeType.REMOVE  시도해보기
    List<Posts> postsList = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, mappedBy ="user",orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public User(String username, String password, String email,UserRoleEnum userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.userRole= userRole;
    }

    public boolean isAdmin() {
        return this.userRole == UserRoleEnum.ADMIN;
    }



    public boolean hasComment(Comment comment) {
        return this.commentList.stream().anyMatch(x ->x.equals(comment));
    }



    public UserRoleEnum getRole() {
        return userRole;
    }
}
