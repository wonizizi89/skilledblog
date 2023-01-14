package com.example.myblog1.common.jwt.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    String findByUsername(String username);

}
