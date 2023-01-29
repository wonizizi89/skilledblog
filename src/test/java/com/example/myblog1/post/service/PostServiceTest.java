package com.example.myblog1.post.service;


import com.example.myblog1.post.dto.PostRequest;
import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.post.entity.Post;
import com.example.myblog1.post.repository.PostRepository;
import com.example.myblog1.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;



    @Test
    @DisplayName("게시글 작성")
    Post createPost() {
        //given
        User user = new User(); //todo user @NoArgsConstructor(access = AccessLevel.PROTECTED) 일 경우 빨간줄 생긴 물어보기
        PostRequest request = new PostRequest();//todo 마찬가지

        /* save 리턴값도 없고, 행위지정(findByUsername)이 없기 때문에 실행한 척만 함
        when(postRepository.save(request.toEntity(user)))
                .thenReturn(Optional.of(any(Post.class)));
                */

        //when
       PostResponse response = postService.createPost(request,user);
        //then
        verify(postRepository,times(1)).save(request.toEntity(user));

        return null;
    }

    @Test
    void getPosts() {
    }

    @Test
    void getSelectPost() {
    }

    @Test
    void updatePost() {
    }
}