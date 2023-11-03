package com.example.demo.jwt;

import com.example.demo.security.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        //home 화면은 토큰 체크 x
        if(!req.getRequestURL().equals("/") ) {
            //access 토큰 값
            String TokenValue = jwtUtil.getJwtFromHeader(req);
            //refresh 토큰 값
            String refreshTokenValue = jwtUtil.getRefreshTokenHeader(req);

            if (StringUtils.hasText(TokenValue)) {

                // JWT 토큰 substring
                TokenValue = jwtUtil.substringToken(TokenValue);

                //access토큰이 유효하면 그대로 반환, 만료되어 refresh토큰 통해 반환되면 새로운 토큰 발급
                String token = jwtUtil.validateToken(TokenValue, refreshTokenValue, res);
                TokenValue = token;

                Claims info = jwtUtil.getUserInfoFromToken(TokenValue);

                try {
                    setAuthentication(info.getSubject());
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return;
                }
            }
        }

        filterChain.doFilter(req, res);
    }

//        String tokenValue = jwtUtil.getJwtFromHeader(req);
//
//        if (StringUtils.hasText(tokenValue)) {
//
//            if (!jwtUtil.validateToken(tokenValue)) {
//                log.error("Token Error");
//                return;
//            }
//
//            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
//
//            try {
//                setAuthentication(info.getSubject());
//            } catch (Exception e) {
//                log.error(e.getMessage());
//                return;
//            }
//        }
//
//        filterChain.doFilter(req, res);
//    }

    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}