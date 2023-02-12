package com.example.myblog1.post.service;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.myblog1.common.exception.CustomException;
import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.post.repository.PostRepository;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.user.entity.UserRoleEnum;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

  @Mock
  private PostRepository postRepository;

  @InjectMocks
  private PostService postService;

  @Spy
  private JwtUtil jwtUtil;


  @BeforeEach
  void prepare() {
    ReflectionTestUtils.setField(jwtUtil,
        "secretKey", // jwtUtil의 secretKey값이 저장될 변수
        "7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA="); // secretKey의 값
    jwtUtil.init(); // jwtUtil에서 @PostConstructor가 동작하지 않기 때문에, 임의로 실행시켜야 함
  }

  @Test
  @DisplayName("게시글 작성")
  void createPost() {
    //given
    User user = new User("pororo", "pororo1234", "pororo@naver.com", UserRoleEnum.USER);
    PostRequest request = new PostRequest("pororo", "oooooooooo");

    // save 리턴값도 없고, 행위지정(findByUsername)이 없기 때문에 실행한 척만 함
    when(postRepository.save(any(Post.class)))
        .thenReturn(request.toEntity(user));

    //when
    postService.createPost(request, user);
    //then
    verify(postRepository, times(1)).save(any(Post.class));

  }

  @Test
    //todo 마지막에 꼭 해보기
  void getPosts() {
    //given
//       Pageable pageable = PageRequest.of(20,10);
//        when(postRepository.findAll(pageable))
//                .thenReturn(any(Page.class));
//        //when
//        PostService.Result response = postService.getPosts(20,10);
//        //then
//        assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("게시글 id가 없을때")
  void getSelectPost_throw() {
    //give
    when(postRepository.findById(anyLong()))
        .thenReturn(Optional.empty());
    //when&then
    assertThrows(CustomException.class, () -> {
      postService.getSelectPost(1L);
    });
  }

  @Test
  @DisplayName("게시글 가져오기")
  void getSelectPost() {
    //given

    User user = new User("pororo", "pororo1234", "pororo@naver.com", UserRoleEnum.USER);
    Post post = new Post("title", "content", user);

    when(postRepository.findById(anyLong()))
        .thenReturn(Optional.of(post));

    //when
    PostResponse response = postService.getSelectPost(anyLong());
    //then
    assertThat(response.getTitle()).isEqualTo(post.getTitle());
    assertThat(response.getContent()).isEqualTo(post.getContent());

    verify(postRepository, times(1)).findById(anyLong());

  }

  @Test
  @DisplayName("업데이트 성공")
    //todo 업데이트하기
  void updatePost() {
    //given

    //when

    //then

  }
}
// @Test
//    @DisplayName("게시글 업데이트")
//    void updatePost() {
//        // given
//        PostRequestDto request = PostRequestDto.builder()
//                .contents("hello.. hello..")
//                .title("hello")
//                .build();
//        User user = new User("nathan", "1234qwer", null, null);
//        Post post = new Post(request, user);
//
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//
//        // when
//        postService.updatePost(anyLong(), request, user);
//
//        // verify
//        verify(postRepository, times(1)).save(any(Post.class));
//    }
//
//    @Test
//    @DisplayName("게시글 업데이트 (다른 사람이 작성하려할때)")
//    void updatePost_invalid_user() {
//        // given
//        PostRequestDto request = PostRequestDto.builder()
//                .contents("hello.. hello..")
//                .title("hello")
//                .build();
//        User userInPost = new User("nathan", "1234qwer", null, null);
//        User userInput = new User("joel", "1234qwer", null, null);
//        Post post = new Post(request, userInPost);
//
//        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
//
//        // when & then
//        assertThrows(IllegalArgumentException.class, () -> postService.updatePost(anyLong(), request, userInput));
//    }
//}