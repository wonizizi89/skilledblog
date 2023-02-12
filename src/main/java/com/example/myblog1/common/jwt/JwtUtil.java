package com.example.myblog1.common.jwt;


import com.example.myblog1.common.security.UserDetailsServiceImpl;
import com.example.myblog1.user.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
  public static final String AUTHORIZATION_KEY = "auth";
  private static final String BEARER_PREFIX = "Bearer ";
  private static final long TOKEN_TIME = 30 * 60 * 1000L; //30 min

  private static final long REFRESH_TOKEN_TIME = 60 * 60 * 1000L * 24 * 14; //2week

  private final UserDetailsServiceImpl userDetailService;
  @Value("${jwt.secret.key}")
  private String secretKey;
  private Key key;
  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }

  // header 토큰을 가져오기
  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  // 토큰 생성
  public String createToken(String username, UserRoleEnum role) {
    Date now = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .setSubject(username)
            .claim(AUTHORIZATION_KEY, role)
            .setExpiration(new Date(now.getTime() + TOKEN_TIME))
            .setIssuedAt(now)
            .signWith(key, signatureAlgorithm)
            .compact();

  }

  public String createRefreshToken(String username) {
    Date now = new Date();

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_TIME))
        .signWith(key, signatureAlgorithm)
        .compact();
  }
//
//    // 토큰 검증
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token, 만료된 JWT token 입니다.");
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
//        }
//        return false;
//    }

  // 토큰에서 사용자 정보 가져오기
  public Claims getUserInfoFromToken(String token) {
    return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
  }


  /**
   * 토큰으로 부터 Authentication 객체를 얻어온다. Authentication 안에 user의 정보가 담겨있음.
   * UsernamePasswordAuthenticationToken 객체로 Authentication을 쉽게 만들수 있으며, 매게변수로 UserDetails, pw,
   * authorities 까지 넣어주면 setAuthenticated(true)로 인스턴스를 생성해주고 Spring-Security는 그것을 체크해서 로그인을 처리함
   */
  //인증 객체 생성
  public Authentication createAuthentication(String username) {

    UserDetails userDetails = userDetailService.loadUserByUsername(username);
    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }


  public boolean validateToken(String token) {
    try {
      Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      return !claims.getBody().getExpiration().before(new Date());
    } catch (SecurityException | MalformedJwtException e) {
      log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
    } catch (ExpiredJwtException e) {
      log.info("Expired JWT token, 만료된 JWT 토큰 입니다.");
    } catch (UnsupportedJwtException e) {
      log.info("Unsupported JWT token, 지원되지 않는 JWT  토큰 입니다. ");
    } catch (IllegalArgumentException e) {
      log.info("JWT claims is empty,잘못된 JWT 토큰 입니다.");
    }
    return false;
  }


}



