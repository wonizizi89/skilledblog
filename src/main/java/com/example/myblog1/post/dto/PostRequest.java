package com.example.myblog1.post.dto;

import com.example.myblog1.post.entity.Post;
import com.example.myblog1.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor(force = true)
public class PostRequest { //클라이언트에서 넘어오는 값을 이 객체를 통해서 받음
    private final String title;
    private final String content;

    @Builder
    public PostRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }

    /* dto -> Entity */
    public Post toEntity(User user){
        return Post.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }
}
