package com.example.myblog1.service;

import com.example.myblog1.dto.CommentRequest;
import com.example.myblog1.dto.CommentResponse;
import com.example.myblog1.dto.ResponseStatusDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {
    public CommentResponse createComment(Long postId,CommentRequest commentRequest, HttpServletRequest request) ;
    public CommentResponse editComment(Long commentId, CommentRequest commentRequest, HttpServletRequest request);
    public ResponseStatusDto deleteComment(Long commentId, HttpServletRequest request);
}
