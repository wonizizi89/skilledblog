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



    //상태코드 구현 추후 구현 안 될시 지울 예정???????????????????
//    @GetMapping(value = "/{id}")
//    public ResponseEntity<Message> findById(@PathVariable long id) {
//        Optional<User> user = userRepository.findById(id);
//        Message message = new Message();
//        HttpHeaders headers = new HttpHeaders();
//        Charset utf8 = Charset.forName("UTF-8");
//        MediaType mediaType = new MediaType("application", "json", utf8);
//        headers.setContentType(mediaType);
//
//        message.setStatus(StatusEnum.OK);
//        message.setMessage("회원가입 성공");
//        message.setData(user);
//
//        return ResponseEntity;
//    }

}

