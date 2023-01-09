package com.example.myblog1.controller;


//import com.example.myblog1.message.Message;
import com.example.myblog1.dto.LoginRequest;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.dto.SignupRequest;
import com.example.myblog1.repository.UserRepository;
import com.example.myblog1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
//    private org.springframework.http.ResponseEntity<Message> ResponseEntity;




    @PostMapping("/signup")
    public ResponseStatusDto signup(@RequestBody @Valid SignupRequest signupRequest) {
        return userService.signup(signupRequest);

    }


    @PostMapping("/login")
    public ResponseStatusDto login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
        //또는 return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto,response)); 간단히 표시가능

    }

}

