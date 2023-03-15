package com.example.myblog1.post.service;

import com.example.myblog1.common.exception.CustomException;
import com.example.myblog1.common.exception.ExceptionStatus;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.post.repository.PostRepository;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  @Transactional
  public PostResponse createPost(PostRequest postRequest, User user) {
    //   Post post = postRepository.save(new Post(postRequest, user));
    Post post = postRepository.save(postRequest.toEntity(user));

    return new PostResponse(post);
  }


  @Transactional
  public Result getPosts(int page, int size, Direction direction, String properties) {
    Page<Post> postsListPage = postRepository.findAll(
        PageRequest.of(page - 1, size, direction, properties));//페이징 셋팅
    if (postsListPage.isEmpty()) {
      throw new CustomException(ExceptionStatus.POST_IS_EMPTY);
    }
    List<PostResponse> postsResponses = postsListPage.stream().map(PostResponse::new)
        .collect(Collectors.toList());
    return new Result(postsResponses.size(), postsResponses);
  }


  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Result<T> {

    private T count;
    private T data;

    public Result(T count, T data) {
      this.count = count;
      this.data = data;
    }
  }


  @Transactional
  public PostResponse getSelectPost(Long id) {
    Post post = postRepository.findById(id).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_EMPTY)
    );
    return new PostResponse(post);


  }

  @Transactional
  public PostResponse updatePost(Long id, PostRequest postRequest, User user) {
    Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_EMPTY)
    );
    post.updatePosts(postRequest);//
    postRepository.saveAndFlush(post);

    return new PostResponse(post);

  }


  @Transactional
  public ResponseStatusDto deletePost(Long id, User user) {
    Post post = postRepository.findByIdAndUserId(id, user.getId()).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_EMPTY)
    );

    postRepository.deleteById(id);
    return new ResponseStatusDto(StatusEnum.POSTS_DELETE_SUCCESS);
  }


  public Post findById(Long postId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new CustomException(ExceptionStatus.POST_IS_EMPTY)
    );
    return post;
  }
}
