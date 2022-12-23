package com.example.myblog1.controller;


import com.example.myblog1.dto.CommentRequest;
import com.example.myblog1.dto.CommentResponse;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.service.CommentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;

    @PostMapping("/comment/{postsId}")
    public CommentResponse createComment(@RequestParam Long postsId,
                                         @RequestBody @Valid CommentRequest commentRequest,
                                         HttpServletRequest request){
        return commentServiceImpl.createComment(postsId,commentRequest,request);
    }
    @PutMapping("/comment/{commentId}")
    public CommentResponse editComment(@RequestParam Long commentId,
                                       @RequestBody CommentRequest commentrequest,
                                       HttpServletRequest request){
        return commentServiceImpl.editComment(commentId,commentrequest,request);
    }

    @DeleteMapping("/comment/{commentId}")
    public ResponseStatusDto deleteComment(Long commentId, HttpServletRequest request) {
        return commentServiceImpl.deleteComment(commentId,request);
    }
}