package com.example.myblog1.comment.service;

import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.common.exception.CustomException;
import com.example.myblog1.common.exception.ExceptionStatus;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.comment.repository.CommentRepository;
import com.example.myblog1.post.repository.PostRepository;
import com.example.myblog1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postsRepository;



    @Override
    @Transactional
    public void createComment(Long postsId, CommentRequest commentRequest, User user) {
        Post post = postsRepository.findByIdAndUserId(postsId,user.getId()).orElseThrow(
                () -> new CustomException(ExceptionStatus.POST_IS_EMPTY)
        );

        //선택한 게시글이 있다면 댓글로 등록하고 등록된 댓글 반환
       // Comment comment = commentRepository.save(new Comment(commentRequest, user, post));
       Comment comment = commentRepository.save(commentRequest.toEntity(user,post));
    }

    @Override
    @Transactional
    public void editComment(Long id, CommentRequest commentRequest, User user) {

        Comment comment = commentRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new CustomException(ExceptionStatus.COMMENT_IS_EMPTY)
        );
        comment.updateComment(commentRequest.getComment());
        commentRepository.save(comment);

    }


    @Override
    @Transactional
    public void deleteComment(Long id, User user) {

        Comment comment = commentRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new CustomException(ExceptionStatus.COMMENT_IS_EMPTY)
        );
        commentRepository.deleteById(id);

    }


}