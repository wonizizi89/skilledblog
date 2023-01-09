package com.example.myblog1.service;

import com.example.myblog1.dto.CommentRequest;
import com.example.myblog1.dto.CommentResponse;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.entity.Comment;
import com.example.myblog1.entity.Posts;
import com.example.myblog1.entity.User;
import com.example.myblog1.jwt.JwtUtil;
import com.example.myblog1.entity.StatusEnum;
import com.example.myblog1.repository.CommentRepository;
import com.example.myblog1.repository.PostsRepository;
import com.example.myblog1.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public CommentResponse createComment(Long postsId, CommentRequest commentRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token error");
            }
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        //선택한 글이 DE저장 유뮤 확인
        Posts posts = postsRepository.findById(postsId).orElseThrow(
                () -> new IllegalArgumentException(("해당 글이 존재하지 않습니다."))
        );

        //선택한 게시글이 있다면 댓글로 등록하고 등록된 댓글 반환
        Comment comment = commentRepository.saveAndFlush(new Comment(commentRequest, user, posts));
        return new CommentResponse(comment);
    }

    @Override
    @Transactional
    public CommentResponse editComment(Long commentId, CommentRequest commentRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token error!");
            }
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 없습니다.")
        );
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 입니다. ")
        );

        comment.updateComment(commentRequest);
        commentRepository.saveAndFlush(comment);

        return new CommentResponse(comment);
    }


    @Override
    @Transactional
    public ResponseStatusDto deleteComment(Long commentId, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token error!");
            }
        }
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 입니다. ")
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

//- 선택한 댓글의 DB 저장 유무를 확인하기
//- 선택한 댓글이 있다면 댓글 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기

        validateComment(comment, user);
        commentRepository.deleteById(commentId);

        return new ResponseStatusDto(StatusEnum.COMMENT_DELETE_SUCCESS);

    }

    private void validateComment(Comment comment, User user) {
        if (!user.isAdmin() && !user.hasComment(comment)) {
            throw new IllegalArgumentException("해당 작성자의 댓글이 아닙니다.");
        }


    }
}