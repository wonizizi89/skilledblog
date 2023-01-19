package com.example.myblog1.user.service;


import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.user.dto.*;
import com.example.myblog1.user.entity.StatusEnum;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.user.entity.UserRoleEnum;
import com.example.myblog1.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private static final String BEARER_PREFIX = "Bearer ";
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseStatusDto signup(@Valid SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = passwordEncoder.encode(signupRequest.getPassword());

        //회원중복확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");
        }

        String email = signupRequest.getEmail();

        //사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (signupRequest.isAdmin()) {
            if (!signupRequest.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능 합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(username, password, email, role);

        userRepository.save(user);
        return new ResponseStatusDto(StatusEnum.SIGNUP_SUCCESS);

    }

    @Transactional // readOnly= true  하면 에러남
    public ResponseStatusDto login(LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, String.valueOf(jwtUtil.createToken(user.getUsername(), user.getUserRole())));
        //response에 헤더쪽 값을 넣어수 있는데 키값에는 AUTHORIZATION_HEADER과 토큰 생성 값을 넣어줌

        return new ResponseStatusDto(StatusEnum.LOGIN_SUCCESS);
    }

    public ResponseStatusDto resignMembership(Long id, ResignRequest resignRequest) {
        User foundUser = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재 하지 않습니다.")
        );
        if (foundUser.getUsername().equals(resignRequest.getUsername())) {
            userRepository.delete(foundUser);
        } else {
            throw new IllegalArgumentException("접근할 수 있는 권한이 없습니다.");
        }

        return new ResponseStatusDto(StatusEnum.USER_DELETE_SUCCESS);
    }

    @Transactional
    public void reissueToken(TokenRequest tokenRequest, HttpServletResponse response) {
        if (!jwtUtil.validateToken(tokenRequest.getRefreshToken())) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        User user = findUserByToken(tokenRequest);

        if (!user.getRefreshToken().equals(tokenRequest.getRefreshToken())) {
            throw new IllegalArgumentException("refreshToken 이 일치 하지 않습니다.");
        }

        //userRepository 에 refreshToken 을 저장
        // refreshToken  재 생성
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());
        user.updateRefreshToken(refreshToken);
        userRepository.saveAndFlush(user);
        //AccessToken  재 생성
        String newAccessToken = jwtUtil.createToken(user.getUsername(),user.getUserRole());

        //AccessToken 발행시 refreshToken 함께 재 발행
        response.addHeader(jwtUtil.AUTHORIZATION_HEADER, newAccessToken);
        response.addHeader(jwtUtil.REFRESH_TOKEN_HEADER, user.getRefreshToken());
    }


        private User findUserByToken(TokenRequest tokenRequest){
         Claims claims = jwtUtil.getUserInfoFromToken(tokenRequest.getAccessToken().substring(7));
         String username = claims.getSubject();
         return userRepository.findByUsername(username).orElseThrow(
                 ()-> new IllegalArgumentException("존재하지 않는 사용자입니다.")
         );
    }
}
