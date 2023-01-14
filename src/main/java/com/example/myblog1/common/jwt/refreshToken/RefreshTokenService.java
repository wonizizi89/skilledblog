package com.example.myblog1.common.jwt.refreshToken;

import com.example.myblog1.common.jwt.JwtUtil;
import com.example.myblog1.common.jwt.TokenDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;




    public String findRefreshTokenByUserName(TokenDto tokenDto){
        Claims claims = jwtUtil.getUserInfoFromToken(tokenDto.getAccessToken().substring(7));
        String username = claims.getSubject();

       return refreshTokenRepository.findByUsername(username);


    }
}
