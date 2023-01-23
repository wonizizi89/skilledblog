package com.example.myblog1.post.service;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.comment.repository.CommentRepository;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.post.repository.PostRepository;
import com.example.myblog1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;


    @Transactional
    public PostResponse createPost(PostRequest postRequest, User user) {
        Post post = postsRepository.save(new Post(postRequest, user));
        return new PostResponse(post);
    }



    @Transactional
    public List<PostResponse> getPosts(int pageChoice){
        Page<Post> postsListPage = postsRepository.findAll(PageRequest.of(pageChoice-1,4,Sort.Direction.DESC,"id"));//페이징 셋팅
        if(postsListPage.isEmpty()){
            throw new IllegalArgumentException("해당 페이지가 존재하지 않습니다.");
        }
        List<PostResponse> postsResponses = postsListPage.stream().map(PostResponse::new).collect(Collectors.toList());
        return postsResponses;

    }


    @Transactional
    public PostResponse getSelectPost(Long id) {
        Post post = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponse(post);



    }

    @Transactional
    public PostResponse updatePost(Long id, PostRequest postRequest, User user) {
        Post post = postsRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재 하지 않습니다.")
        );
        post.updatePosts(postRequest);
        postsRepository.saveAndFlush(post);

        return new PostResponse(post);

    }


    @Transactional
    public ResponseStatusDto deletePost(Long id, User user) {
        Post post = postsRepository.findByIdAndUserId(id,user.getId()).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );

        postsRepository.deleteById(id);
        return new ResponseStatusDto(StatusEnum.POSTS_DELETE_SUCCESS);
    }

}
