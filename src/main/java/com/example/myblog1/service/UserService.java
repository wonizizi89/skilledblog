package com.example.myblog1.service;


import com.example.myblog1.dto.LoginRequest;
import com.example.myblog1.dto.SignupRequest;
import com.example.myblog1.entity.User;
import com.example.myblog1.entity.UserRoleEnum;
import com.example.myblog1.jwt.JwtUtil;
import com.example.myblog1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    @Transactional
    public void signup(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        String password = signupRequest.getPassword();

        //회원중복확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다.");//statuCode:400 에러메시지와 같이 반환
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
        System.out.println(username);
        System.out.println(password);
        System.out.println(email);
        System.out.println(role);
        userRepository.save(user);

    }

    @Transactional // readOnly= true  하면 에러남
    public void login(LoginRequest loginRequest, HttpServletResponse response) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //비밀번호 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다. ");
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getRole()));
        //response에 헤더쪽 값을 넣어수 있는데 키값에는 AUTHORIZATION_HEADER과 토큰 생성 값을 넣어줌
    }

}
