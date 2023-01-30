package com.example.myblog1.user.controller;

import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.dto.ResponseStatusDto;
import com.example.myblog1.user.dto.SignupRequest;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.user.service.UserService;
import com.google.gson.Gson;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;
    private MockMvc mockMvc;


    @BeforeEach
    public void init() { // mockMvc 초기화, 각메서드가 실행되기전에 초기화 되게 함
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        //  standaloneMockMvcBuilder() 호출하고 스프링 테스트의 설정을 커스텀하여 mockMvc 객체 생성
    }

    @Test
    @DisplayName("회원가입 (성공)")
    void signup1() throws Exception {
        //given
        // request 입력값, 목객체 리턴값
        SignupRequest request = SignupRequest.builder()
                .username("pororo")
                .password("pororo1234")
                .email("pororo@naver.com")
                .admin(false)
                .build();
        ResponseStatusDto response = new ResponseStatusDto(StatusEnum.SIGNUP_SUCCESS);

        when(userService.signup(any(SignupRequest.class)))//when(userService.signup(request)) 이건 에러남 아래에서 Gson으로 뉴한 객체와 주소값이 다르기 때문임
                .thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request))); //json으로 받아야 하기 때문에 Gson을 build gradle에 추가 하기


        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("statusCode", response.getStatusCode()).exists())
                .andExpect(jsonPath("msg", response.getMsg()).exists());
    }

    @Test
    @DisplayName("회원가입 (실패) - 아이디")
    void signup_failed_id() throws Exception {
        // given
        SignupRequest request = SignupRequest.builder()
                .admin(false)
                .username("nat")
                .password("1234qwer")
                .email("nathan@gmail.com")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request)));

        // then
        resultActions.andExpect(status().isBadRequest());
    }


    @Test
    @DisplayName("회원가입 (실패) - 패스워드")
    void signup_failed_pw() throws Exception {
        //given
        //request, response
        SignupRequest request = SignupRequest.builder()
                .admin(false)
                .username("pororo")
                .password("123")
                .email("pororo@naver.com")
                .build();

        ResponseStatusDto response = new ResponseStatusDto(StatusEnum.SIGNUP_SUCCESS);
        lenient().when(userService.signup(request))
                .thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(request)));

        //then
        resultActions.andExpect(status().isBadRequest());


    }

}