package com.example.myblog1.user.repository;

import com.example.myblog1.user.entity.User;
import com.example.myblog1.user.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest //@DataJpaTest annotation 을 제공하는데, 이것을 통해 Repository 의 단위 테스트가 가능함
class UserRepositoryTest {
    // 레포지토리 하단은 데이터 베이스이므로 목객체가 필요없음

    //테스트 할 레포지토리는 빈으로 등록되므로, @Autowiried 를 통해 의존성을 주입 받음
    @Autowired
    private UserRepository userRepository;


    @Test
    @DisplayName("사용자 추가")
        //findByUsername 이 실행 되려면 save가 일어나야 함으로 사용자가 추가되는지 먼저 테스트
    void addUser() {
        //given
        User user = new User(
                "pororo",
                "pororo1234",
                "proro@naver.com",
                UserRoleEnum.USER); //null 로 하게 에러 처리 됨 >.<ㅎㅎ 잘 처리됨 굿

        //when
        User savedUser = userRepository.save(user);
        //then
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
    }


    @Test
    @DisplayName("사용자 조회")
    void findByUsername() {
        //given
        User user = new User(
                "pororo",
                "pororo1234",
                "pororo@naver.com",
                UserRoleEnum.USER
        );

        userRepository.save(user);
        //when
        Optional<User> savedUser = userRepository.findByUsername(user.getUsername());
        //then
        assertThat(savedUser).isPresent();

    }
}