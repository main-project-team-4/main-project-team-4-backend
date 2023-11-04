package com.example.demo.jwt;

import com.example.demo.refreshToken.RefreshToken;
import com.example.demo.refreshToken.RefreshTokenService;
import com.example.demo.repository.RedisRepository;
import com.example.demo.security.UserRoleEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String AUTHORIZATION_KEY = "auth";

    public static final String BEARER_PREFIX = "Bearer ";

    public static final String REFRESH_HEADER = "Refresh";

    //redis 값 조회 헤더
    public static final String REFRESH_PREFIX = "refresh:";

    private final long TOKEN_TIME = 30 * 1000L;

//    private final long TOKEN_TIME = 30 * 1000L;

    private final RedisRepository redisRepository;

    private final RefreshTokenService refreshTokenService;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String createTestToken(String username) {
        Calendar calendar = new GregorianCalendar(3000, Calendar.FEBRUARY, 1);

        return BEARER_PREFIX +
                Jwts.builder()
                        .setId(username)
                        .setSubject(username)
                        .claim(AUTHORIZATION_KEY, UserRoleEnum.USER)
                        .setExpiration(calendar.getTime())
                        .setIssuedAt(new Date())
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
//        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
//        return null;
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        log.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // refreshToken 생성
    public String getRefreshTokenHeader(HttpServletRequest request) {
//        String refreshToken = request.getHeader(REFRESH_HEADER);
//        return null;
        return request.getHeader(REFRESH_HEADER);
    }


    public String extractJwt(final StompHeaderAccessor accessor) {
        return accessor.getFirstNativeHeader("Authorization");
    }


    // 토큰 검증
    public String validateToken(String TokenValue, String refreshTokenValue, HttpServletResponse res) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(TokenValue);
            return TokenValue;

        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
            throw new JwtException("Invalid JWT signature, 유효하지 않은 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            //refresh 토큰 값전달해서 유효 확인
            String value = redisRepository.getValue(REFRESH_PREFIX + refreshTokenValue);
            if (value == null) { // refresh 만료
                log.error("Expired JWT token, 만료된 JWT token 입니다.");
                throw new JwtException("Expired JWT, 만료된 JWT 입니다.");
            }
            try {
                ObjectMapper objectMapper = new ObjectMapper();

                //refreshToken 값
                RefreshToken refreshToken = objectMapper.readValue(value, RefreshToken.class);

                String username = refreshToken.getUsername();
                //access 토큰 다시 발급 (Bearer ~~)
                TokenValue = createToken(username, UserRoleEnum.USER);

                //Refresh Token Rotation (기존 Refresh 토큰 제거 후 새로 발급)
                Long refreshExpireTime = refreshTokenService.getRefreshTokenTimeToLive(REFRESH_PREFIX + refreshTokenValue);
                redisRepository.setExpire(REFRESH_PREFIX + refreshTokenValue, 0L);

                String newRefreshToken = refreshTokenService.refreshTokenRotation(username, refreshExpireTime);

                // Header 에 토큰 추가
                res.addHeader(JwtUtil.AUTHORIZATION_HEADER, TokenValue);

                // Header 에 refresh 토큰 추가
                res.addHeader(JwtUtil.REFRESH_HEADER, newRefreshToken);

                //Bearer 제거
                TokenValue = substringToken(TokenValue);
                return TokenValue;

            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
            throw new JwtException("Unsupported JWT, 지원되지 않는 JWT 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
            throw new JwtException("JWT claims is empty, 잘못된 JWT 입니다.");
        }
    }


    // StompHandler 토큰 검증
    public boolean validateTokenWebsocket(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getId();
    }
}