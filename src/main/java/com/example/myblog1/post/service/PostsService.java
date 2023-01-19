package com.example.myblog1.post.service;

import com.example.myblog1.comment.entity.Comment;
import com.example.myblog1.comment.repository.CommentRepository;
import com.example.myblog1.post.dto.PostsRequest;
import com.example.myblog1.post.dto.PostsResponse;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.post.repository.PostsRepository;
import com.example.myblog1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CommentRepository commentRepository;


    @Transactional
    public PostsResponse createPosts(PostsRequest postsRequest, User user) {
        Posts posts = postsRepository.saveAndFlush(new Posts(postsRequest, user));
        return new PostsResponse(posts);
    }



    @Transactional
    public List<PostsResponse> getPostsList(int pageChoice){
        Page<Posts> postsListPage = postsRepository.findAll(pageableSetting(pageChoice));
        if(postsListPage.isEmpty()){
            throw new IllegalArgumentException("해당 페이지가 존재하지 않습니다.");
        }
        List<PostsResponse> postsListResponseList = postsListPage.stream().map(PostsResponse::new).collect(Collectors.toList());
        //todo comment list 함께보여주기
        //현 포스트에 해당하는 커멘트들을 가져와서 리턴
        //포스트1 . 포스트 1.댓글 1/ 댓글2 ....대댓글아님

        return postsListResponseList;

    }

    private Pageable pageableSetting(int pageChoice) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction,"id");
        Pageable pageable = PageRequest.of(pageChoice-1,4,sort);
        return pageable;
    }

    @Transactional
    public PostsResponse getSelectPosts(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );

        //코멘트 같이 보여주기 해당 게시글의 코멘들 다 보여주기
//        List<Comment> commentList = commentRepository.findAllById(Collections.singleton(id));
//        return new PostsResponse(posts,commentList);
       return new PostsResponse(posts);
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
