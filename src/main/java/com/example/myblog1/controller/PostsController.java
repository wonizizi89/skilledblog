package com.example.myblog1.controller;


import com.example.myblog1.dto.PostsRequest;
import com.example.myblog1.dto.PostsResponse;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.entity.Posts;
import com.example.myblog1.service.PostsService;
import lombok.RequiredArgsConstructor;
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
    public PostsResponse createPosts(@RequestBody PostsRequest postsRequest, HttpServletRequest request) {
        PostsResponse response =postsService.createPosts(postsRequest, request);
        return response;
    }
//    @GetMapping("/posts")
//    public List<ResponsePostDto> getPosts() {
//        return postService.getPostList();
//    }
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
    public PostsResponse updatePosts(@PathVariable Long id, @RequestBody PostsRequest postsRequest, HttpServletRequest request) {
        return postsService.updatePosts(id, postsRequest,request);
    }

    //선택한 포스트 삭제
    @DeleteMapping("/{id}")
    public ResponseStatusDto deletePosts(@PathVariable Long id, HttpServletRequest request ) {
        return postsService.deletePosts(id,request);
    }
}
