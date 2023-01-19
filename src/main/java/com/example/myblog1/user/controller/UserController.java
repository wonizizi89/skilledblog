package com.example.myblog1.user.controller;


//import com.example.myblog1.message.Message;

import com.example.myblog1.common.security.UserDetailsImpl;
import com.example.myblog1.post.dto.PostsResponse;
import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.dto.TokenRequest;
import com.example.myblog1.user.dto.LoginRequest;
import com.example.myblog1.user.dto.ResignRequest;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.dto.SignupRequest;
import com.example.myblog1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {


    private final UserService userService;


    @PostMapping("/signup")
    public ResponseStatusDto signup(@RequestBody @Valid SignupRequest signupRequest) {
        return userService.signup(signupRequest);

    }


    @PostMapping("/login")
    public ResponseStatusDto login(@RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
        //또는 return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto,response)); 간단히 표시가능

    }

    @DeleteMapping("/resign/{id}")
    public ResponseStatusDto resignMembership(@PathVariable Long id, @RequestBody ResignRequest resignRequest) {
        return userService.resignMembership(id, resignRequest);
    }

    //title or content 검색기능 ,페이징
    @GetMapping("/keyword")
    public List<PostsResponse> searchByKeyword(@RequestParam String title, @RequestParam String content ,@RequestParam int pageChoice){
        return userService.searchByKeyword(title,content,pageChoice);
    }

}

