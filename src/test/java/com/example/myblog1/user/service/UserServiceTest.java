package com.example.myblog1.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.dto.LoginRequest;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.dto.SignupRequest;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.user.entity.UserRoleEnum;
import com.example.myblog1.user.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  @Mock
  private UserRepository userRepository;
  @InjectMocks
  private UserService userService;

  @InjectMocks
  private JwtUtil jwtUtil;

  @BeforeEach
  void prepare() {
    ReflectionTestUtils.setField(jwtUtil,
        "secretKey", // jwtUtil의 secretKey값이 저장될 변수
        "7ZWt7ZW0OTntmZTsnbTtjIXtlZzqta3snYTrhIjrqLjshLjqs4TroZzrgpjslYTqsIDsnpDtm4zrpa3tlZzqsJzrsJzsnpDrpbzrp4zrk6TslrTqsIDsnpA="); // secretKey의 값
    jwtUtil.init(); // jwtUtil에서 @PostConstructor가 동작하지 않기 때문에, 임의로 실행시켜야 함
  }

  @Spy
  private BCryptPasswordEncoder passwordEncoder;


  @Test
  @DisplayName("회원가입")
  void signup() {
    //given 입력값 리턴값
    SignupRequest request = SignupRequest.builder()
        .username("pororo")
        .password("pororo1234")
        .email("pororo@naver.com")
        .admin(false)
        .adminToken(null)
        .build();

    when(userRepository.findByUsername(any(String.class)))
        .thenReturn(Optional.empty());
    //when
    ResponseStatusDto response = userService.signup(request);

    //then
    assertThat(response.getStatusCode()).isEqualTo(StatusEnum.SIGNUP_SUCCESS.getStatusCode());
    assertThat(response.getMsg()).isEqualTo(StatusEnum.SIGNUP_SUCCESS.getMsg());

    verify(userRepository, times(1)).save(any(User.class));
  }

  @Test
  @DisplayName("로그인")
  void login() {   // 에러 왜 발생하는지 체크하기 -> 이유 : jwtUtil에서 @PostConstructor가 동작하지 않기 때문에, 임의로 실행시켜주니까 에러해결됨
    //given
    LoginRequest request = LoginRequest.builder()
        .username("pororo")
        .password("12341234")
        .build();
    MockHttpServletResponse servletResponse = new MockHttpServletResponse();//웹서버가 관장하는 값을 넣어줌

    //목객체의 리턴값
    User user = new User("pororo", passwordEncoder.encode("12341234"), "pororo@naver.com",
        UserRoleEnum.USER);
    when(userRepository.findByUsername(any(String.class)))
        .thenReturn(Optional.of(user));

    //when
    ResponseStatusDto response = userService.login(request, servletResponse);
    //then
    assertThat(response.getStatusCode()).isEqualTo(StatusEnum.LOGIN_SUCCESS.getStatusCode());
    assertThat(response.getMsg()).isEqualTo(StatusEnum.LOGIN_SUCCESS.getMsg());
    assertThat(servletResponse.getHeaderValue("Authorization").toString()).isNotEmpty();

    verify(userRepository, times(1)).findByUsername(any(String.class));

  }
}