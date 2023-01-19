package com.example.myblog1;

import com.example.myblog1.post.entity.Posts;
import com.example.myblog1.user.entity.User;
import com.example.myblog1.user.entity.UserRoleEnum;
import com.example.myblog1.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class initData implements ApplicationRunner {

    private  final PasswordEncoder passwordEncoder ;
    private final UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) throws Exception {

        User user1 = new User("pororo", passwordEncoder.encode("pororopororo"),"pororo@naver.com", UserRoleEnum.USER );
        User user2 = new User("crong", passwordEncoder.encode("crongcrong"),"crong@naver.com", UserRoleEnum.USER );
        User user3 = new User("rupi", passwordEncoder.encode("rupirupi"),"rupi@naver.com", UserRoleEnum.USER );

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);


    }
}
