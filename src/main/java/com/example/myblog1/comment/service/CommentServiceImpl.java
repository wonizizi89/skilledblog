package com.example.myblog1.comment.service;

import com.example.myblog1.comment.dto.CommentRequest;
import com.example.myblog1.comment.entity.Comment;
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

    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
    private final PostRepository postsRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void createComment(Long postsId, CommentRequest commentRequest, User user) {
        Post post = postsRepository.findByIdAndUserId(postsId,user.getId()).orElseThrow(
                () -> new NullPointerException(("해당 글이 존재하지 않습니다."))
        );

        //선택한 게시글이 있다면 댓글로 등록하고 등록된 댓글 반환
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequest, user, post));

    }

    @Override
    @Transactional
    public void editComment(Long id, CommentRequest commentRequest, User user) {

        Comment comment = commentRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        comment.updateComment(commentRequest);
        commentRepository.saveAndFlush(comment);

    }


    @Override
    @Transactional
    public void deleteComment(Long id, User user) {

        Comment comment = commentRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );
        commentRepository.deleteById(id);

    }

//    private void validateComment(Comment comment, User user) {
//        if (!user.isAdmin() && !user.hasComment(comment)) {
//            throw new IllegalArgumentException("해당 작성자의 댓글이 아닙니다.");
//        }
//
//    }
}