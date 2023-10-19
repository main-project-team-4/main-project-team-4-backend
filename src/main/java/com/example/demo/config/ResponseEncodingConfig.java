package com.example.demo.config;

import com.example.demo.jwt.JwtAuthorizationFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class ResponseEncodingConfig {
    @Bean
    public Customizer<HttpSecurity> responseEncoding() {
        return http -> http.addFilterBefore(responseEncodingFilter(), JwtAuthorizationFilter.class);
    }

    private OncePerRequestFilter responseEncodingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain filterChain
            ) throws ServletException, IOException {

                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                filterChain.doFilter(request, response);
            }
        };
    }
}
