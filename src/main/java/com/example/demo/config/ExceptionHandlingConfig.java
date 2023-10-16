package com.example.demo.config;

import com.example.demo.exception.AccessDeniedHandlerImpl;
import com.example.demo.exception.AuthenticationEntryPointImpl;
import com.example.demo.util.FilterChainRing;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExceptionHandlingConfig {
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public FilterChainRing configureExceptionHandlingConfig() {
        return http -> http
                .exceptionHandling(b -> b
                        .accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint)
                );
    }
}
