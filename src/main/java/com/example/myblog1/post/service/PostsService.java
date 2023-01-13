package com.example.myblog1.post.service;

import com.example.myblog1.post.dto.PostsRequest;
import com.example.myblog1.post.dto.PostsResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.post.repository.PostsRepository;
import com.example.myblog1.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public PostsResponse createPosts(PostsRequest postsRequest, HttpServletRequest request) {

        //Request 에서 token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        if (token != null) {
            //Token 검증
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            //요청받은 dto로 db에 저장할 객체 만들기
            Posts posts = postsRepository.saveAndFlush(new Posts(postsRequest, user));

            return new PostsResponse(posts);
        } else {
            return null;
        }

    }

    @Transactional
    public List<PostsResponse> getPosts() {
        return PostsResponse.of( postsRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public PostsResponse getSelectPosts(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return PostsResponse.of(posts);
    }

    //    토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
// 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    @Transactional
    public PostsResponse updatePosts(Long id, PostsRequest postsRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }
            //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Posts posts = postsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재 하지 않습니다.")
            );
             posts.updatePosts(postsRequest);
             postsRepository.saveAndFlush(posts);
            return new PostsResponse(posts);

        } else {
            return null;
        }

    }


    @Transactional
    public ResponseStatusDto deletePosts(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Posts posts = postsRepository.findById(id).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );

            postsRepository.deleteById(id);
        }
        return new ResponseStatusDto(StatusEnum.POSTS_DELETE_SUCCESS);
    }

}
