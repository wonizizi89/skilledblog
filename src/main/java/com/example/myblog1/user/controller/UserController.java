package com.example.myblog1.user.controller;


//import com.example.myblog1.message.Message;
import com.example.myblog1.user.dto.TokenRequest;
import com.example.myblog1.user.dto.LoginRequest;
import com.example.myblog1.user.dto.ResignRequest;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.dto.SignupRequest;
import com.example.myblog1.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    //todo 회원탈퇴 구현
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
    public ResponseStatusDto resignMembership(@PathVariable Long id,@RequestBody ResignRequest resignRequest){
        return userService.resignMembership(id,resignRequest);
    }

    @PostMapping("/reissue")
    public void reissueToken(@RequestBody TokenRequest tokenRequest, HttpServletResponse response){
        userService.reissueToken(tokenRequest, response);
    }


}

