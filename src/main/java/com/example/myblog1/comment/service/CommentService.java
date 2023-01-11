package com.example.myblog1.comment.service;

import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.dto.CommentResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {
    public CommentResponse createComment(Long postId,CommentRequest commentRequest, HttpServletRequest request) ;
    public CommentResponse editComment(Long commentId, CommentRequest commentRequest, HttpServletRequest request);
    public ResponseStatusDto deleteComment(Long commentId, HttpServletRequest request);
}
