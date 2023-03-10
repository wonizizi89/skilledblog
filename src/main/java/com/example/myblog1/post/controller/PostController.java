package com.example.myblog1.post.controller;


import com.example.myblog1.common.security.UserDetailsImpl;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.post.service.PostService;
import com.example.myblog1.user.dto.ResponseStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  //포스트 글 생성
  @PostMapping("")
  public PostResponse createPost(@RequestBody PostRequest postRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    PostResponse response = postService.createPost(postRequest, userDetails.getUser());
    return response;
  }

  //포스트 조회

  @GetMapping("/page")
  public PostService.Result getPosts(
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "size", required = false, defaultValue = "10") int size,
      @RequestParam(value = "direction", required = false, defaultValue = "desc") Direction direction,
      @RequestParam(value = "properties", required = false, defaultValue = "createdDate") String properties) {
    return postService.getPosts(page, size, direction, properties);

  }

  //해당 포스트 조회

  @GetMapping("/{id}")
  public PostResponse getSelectPost(@PathVariable Long id) {
    return postService.getSelectPost(id);
  }


  //선택한 포스트 수정 API
  @PatchMapping("/{id}")
  public PostResponse updatePost(@PathVariable Long id, @RequestBody PostRequest postRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.updatePost(id, postRequest, userDetails.getUser());
  }

  //선택한 포스트 삭제
  @DeleteMapping("/{id}")
  public ResponseStatusDto deletePost(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    return postService.deletePost(id, userDetails.getUser());
  }
}
