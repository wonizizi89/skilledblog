package com.example.myblog1.controller;


import com.example.myblog1.dto.LoginRequest;
import com.example.myblog1.dto.ResponseStatusDto;
import com.example.myblog1.dto.SignupRequest;
import com.example.myblog1.entity.User;
//import com.example.myblog1.message.Message;
import com.example.myblog1.message.StatusEnum;
import com.example.myblog1.repository.UserRepository;
import com.example.myblog1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
//    private org.springframework.http.ResponseEntity<Message> ResponseEntity;


    //??? 포스트 맵핑이면 바디로 데이터가 들어오니 @requestBody 사용 하지 않나??

    @PostMapping("/signup")
    public ResponseStatusDto signup(@RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);

    }


    @PostMapping("/login")
    public ResponseStatusDto login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        return userService.login(loginRequest, response);
        //또는 return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto,response)); 간단히 표시가능

    }




//    //상태코드 구현 추후 구현 안 될시 지울 예정???????????????????
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

//이제는 ajax에서 body에 값이 넘어오기 때문에 이렇게 @RequestBody를 써주셔야 함

//HttpServletResponse 는 우리가 HttpRequest에서 헤더가 넘어와 받아오는 것처럼
//우리도 클라이언트쪾으로 반환 할 때는  이렇게 Response객체를 반환함
//그래서 이것도 미리 가지고 와서 반환할 response Header에다가 우리가 만들어준 토큰을 넣이주기 위해서
//이렇게 받아오고 있음