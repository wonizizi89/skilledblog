package com.example.myblog1.post.service;

import com.example.myblog1.common.security.UserDetailsImpl;
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
    public PostsResponse createPosts(PostsRequest postsRequest, User user) {
        Posts posts = postsRepository.saveAndFlush(new Posts(postsRequest, user));
        return new PostsResponse(posts);
    }


    @Transactional
    public List<PostsResponse> getPosts() {
        return PostsResponse.of(postsRepository.findAllByOrderByModifiedAtDesc());
    }

    @Transactional
    public PostsResponse getSelectPosts(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return PostsResponse.of(posts);
    }

    @Transactional
    public PostsResponse updatePosts(Long id, PostsRequest postsRequest, User user) {
        Posts posts = postsRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재 하지 않습니다.")
        );
        posts.updatePosts(postsRequest);
        postsRepository.saveAndFlush(posts);

        return new PostsResponse(posts);

    }


    @Transactional
    public ResponseStatusDto deletePosts(Long id, User user) {
        Posts posts = postsRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        postsRepository.deleteById(id);
        return new ResponseStatusDto(StatusEnum.POSTS_DELETE_SUCCESS);
    }

}
