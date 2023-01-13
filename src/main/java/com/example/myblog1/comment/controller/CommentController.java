package com.example.myblog1.comment.controller;


import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.dto.CommentResponse;
import com.example.myblog1.common.security.UserDetailsImpl;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.comment.service.CommentServiceImpl;
import com.example.myblog1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;
    private final UserRepository userRepository;

    @PostMapping("/posts/{id}")
    public ResponseEntity createComment(@PathVariable Long id,
                                        @RequestBody @Valid CommentRequest commentRequest,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
         commentServiceImpl.createComment(id,commentRequest,userDetails.getUser());
         return ResponseEntity.ok("댓글 작성 완료");
    }

    @PutMapping("/{id}")
    public ResponseEntity editComment(@PathVariable Long id,
                                       @RequestBody @Valid CommentRequest commentRequest,
                                       @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentServiceImpl.editComment(id,commentRequest,userDetails.getUser());
        return ResponseEntity.ok("수정 완료");
                //ResponseEntity.status(HttpStatus.OK).body("댓글 수정 완료");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
         commentServiceImpl.deleteComment(id,userDetails.getUser());
        return ResponseEntity.ok("댓글 삭제 완료");
    }
}