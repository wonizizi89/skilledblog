package com.example.myblog1.post.controller;


import com.example.myblog1.common.security.UserDetailsImpl;
import com.example.myblog1.post.dto.PostsRequest;
import com.example.myblog1.post.dto.PostsResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.post.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {

    private final PostsService postsService;

    //포스트 글 생성
    @PostMapping("")
    public PostsResponse createPosts(@RequestBody PostsRequest postsRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostsResponse response =postsService.createPosts(postsRequest, userDetails.getUser());
        return response;
    }

    //포스트 조회
    @GetMapping("")
    public List<PostsResponse> getPosts() {
        return postsService.getPosts();

    }

    //해당 포스트 조회
    @GetMapping("/{id}")
    public PostsResponse getSelectPosts(@PathVariable Long id) {
        return postsService.getSelectPosts(id);
    }



  //선택한 포스트 수정 API
    @PutMapping("/{id}")
    public PostsResponse updatePosts(@PathVariable Long id, @RequestBody PostsRequest postsRequest, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postsService.updatePosts(id, postsRequest,userDetails.getUser());
    }

    //선택한 포스트 삭제
    @DeleteMapping("/{id}")
    public ResponseStatusDto deletePosts(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        return postsService.deletePosts(id,userDetails.getUser());
    }
}
