package com.example.myblog1.comment.controller;


import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.service.CommentServiceImpl;
import com.example.myblog1.common.security.UserDetailsImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentServiceImpl commentServiceImpl;


  @PostMapping("/posts/{id}")
  public ResponseEntity createComment(@PathVariable Long id,
      @RequestBody @Valid CommentRequest commentRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentServiceImpl.createComment(id, commentRequest, userDetails.getUser());
    return ResponseEntity.ok("댓글 작성 완료");
  }

  @PutMapping("/{id}")
  public ResponseEntity editComment(@PathVariable Long id,
      @RequestBody @Valid CommentRequest commentRequest,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentServiceImpl.editComment(id, commentRequest, userDetails.getUser());
    return ResponseEntity.ok("수정 완료");


  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteComment(@PathVariable Long id,
      @AuthenticationPrincipal UserDetailsImpl userDetails) {
    commentServiceImpl.deleteComment(id, userDetails.getUser());
    return ResponseEntity.ok("댓글 삭제 완료");
  }
}