package com.example.myblog1.comment.service;

import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.dto.CommentResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;

import javax.servlet.http.HttpServletRequest;

public interface CommentService {
    public void createComment(Long id,CommentRequest commentRequest, HttpServletRequest request) ;
    public void editComment(Long id, CommentRequest commentRequest, HttpServletRequest request);
    public void deleteComment(Long id, HttpServletRequest request);
}
