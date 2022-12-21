package com.example.myblog1.service;

import com.example.myblog1.dto.PostsRequest;
import com.example.myblog1.dto.PostsResponse;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.entity.Posts;
import com.example.myblog1.entity.User;
import com.example.myblog1.jwt.JwtUtil;
import com.example.myblog1.message.StatusEnum;
import com.example.myblog1.repository.PostsRepository;
import com.example.myblog1.repository.UserRepository;
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



//    2. 게시글 작성 API
//    - 토큰을 검사하여, 유효한 토큰일 경우에만 게시글 작성 가능
//    - 제목, 작성자명(username), 작성 내용을 저장하고
//    - 저장된 게시글을 Client 로 반환하기

    @Transactional
    public PostsResponse createPosts(PostsRequest postsRequest, HttpServletRequest request) {
        //Request 에서 token 가져오기

        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }
        }
        //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        //요청받은 dto로 db에 저장할 객체 만들기
        Posts posts = postsRepository.saveAndFlush(new Posts(postsRequest,user));

        return new PostsResponse(posts);

    }
    @Transactional
    public List<Posts> getPosts() {
        return postsRepository.findAllByOrderByModifiedAtDesc();
    }
    @Transactional
    public Posts getSelectPosts(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(  //???????비밀번호가 일치하면 수정이 일어남///
                ()-> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return posts;
    }

    //- 토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 수정 가능
//- 제목, 작성 내용을 수정하고 수정된 게시글을 Client 로 반환하기
    @Transactional
    public Posts updatePosts(Long id, PostsRequest postsRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }
        }
        //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Posts posts = postsRepository.findById(id).orElseThrow(

        );
        posts.updatePosts(postsRequest);
        return posts;
    }


    //    토큰을 검사한 후, 유효한 토큰이면서 해당 사용자가 작성한 게시글만 삭제 가능
    //선택한 게시글을 삭제하고 Client 로 성공했다는 메시지, 상태코드 반환하기
    @Transactional
    public ResponseStatusDto deletePosts(Long id, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims = null;

        if(token != null){
            if(jwtUtil.validateToken(token)){
                claims = jwtUtil.getUserInfoFromToken(token);
            }else{
                throw new IllegalArgumentException("Token Error");
            }
        }
        //토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                ()-> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Posts posts = postsRepository.findById(id).orElseThrow(

        );

        postsRepository.deleteById(id);
        return new ResponseStatusDto(StatusEnum.DELETE_SUCCESS);

    }


}
