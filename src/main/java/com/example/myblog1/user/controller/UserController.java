package com.example.myblog1.user.controller;

//import com.example.myblog1.message.Message;

import com.example.myblog1.post.dto.PostResponse;
import com.example.myblog1.user.dto.LoginRequest;
import com.example.myblog1.user.dto.ResignRequest;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.dto.SignupRequest;
import com.example.myblog1.user.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public ResponseStatusDto login(@RequestBody @Valid LoginRequest loginRequest,
      HttpServletResponse response) {
    return userService.login(loginRequest, response);
    //또는 return ResponseEntity.status(HttpStatus.OK).body(userService.login(loginRequestDto,response)); 간단히 표시가능

  }

  @DeleteMapping("/resign/{id}")
  public ResponseStatusDto resignMembership(@PathVariable Long id,
      @RequestBody ResignRequest resignRequest) {
    return userService.resignMembership(id, resignRequest);
  }

  //title or content 검색기능 ,페이징
  @GetMapping("/keyword")
  public List<PostResponse> searchByKeyword(
      @RequestParam String keyword,
      @RequestParam(value = "page", required = false, defaultValue = "1") int page,
      @RequestParam(value = "size", required = false, defaultValue = "2") int size,
      @RequestParam(value = "direction", required = false) Direction direction,
      @RequestParam(value = "properties", required = false) String properties
  ) {
    return userService.searchByKeyword(keyword, page, size, direction, properties);
  }

}

