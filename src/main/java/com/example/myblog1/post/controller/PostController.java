package com.example.myblog1.post.controller;


import com.example.myblog1.common.security.UserDetailsImpl;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    //포스트 글 생성
    @PostMapping("")
    public PostResponse createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponse response = postService.createPost(postRequest, userDetails.getUser());
        return response;
    }

    //포스트 조회

    @GetMapping("/page/{postChoice}")
    public List<PostResponse> getPosts(@PathVariable int postChoice) {
        return postService.getPosts(postChoice);

    }

    //해당 포스트 조회

    @GetMapping("/{id}")
    public PostResponse getSelectPost(@PathVariable Long id) {
        return postService.getSelectPost(id);
    }



  //선택한 포스트 수정 API
    @PutMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.updatePost(id, postRequest,userDetails.getUser());
    }

    //선택한 포스트 삭제
    @DeleteMapping("/{id}")
    public ResponseStatusDto deletePost(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        return postService.deletePost(id,userDetails.getUser());
    }
}
