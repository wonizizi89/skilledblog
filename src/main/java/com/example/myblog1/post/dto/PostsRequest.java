package com.example.myblog1.post.dto;

import lombok.Getter;

@Getter
public class PostsRequest { //클라이언트에서 넘어오는 값을 이 객체를 통해서 받음
    private String title;
    private String content;


}
