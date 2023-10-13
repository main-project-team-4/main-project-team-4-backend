package com.example.demo.config;

import com.example.demo.jwt.JwtAuthenticationFilter;
import com.example.demo.jwt.JwtAuthorizationFilter;
import com.example.demo.jwt.JwtUtil;
import com.example.demo.security.UserDetailsServiceImpl;
import com.example.demo.util.FilterChainRingContainer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final FilterChainRingContainer filterChainRingContainer;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(jwtUtil);
        filter.setAuthenticationManager(authenticationManager(authenticationConfiguration));
        return filter;
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter(jwtUtil, userDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 각종 추가 설정
        filterChainRingContainer.configure(http);

        // CSRF 설정
        http.csrf((csrf) -> csrf.disable());

        http.sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers(antMatcher("/api/auth/**")).permitAll()

                        // 카테고리 API
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/categories")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/categories/*")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/categories/*/categories")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/categories/all/categories")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/categories/*/items")).permitAll()

                        // swagger
                        .requestMatchers(antMatcher("/")).permitAll()
                        .requestMatchers(antMatcher("/v3/api-docs/**")).permitAll()
                        .requestMatchers(antMatcher("/swagger-ui/**")).permitAll()

                        // 검색 API
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/items")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/top-items")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/nearby-items")).authenticated()

                        // 찜 API
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/items/*/wishes")).authenticated()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/mypages/wishlists")).authenticated()

                        // 팔로우 관련 API
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/members/*/followers")).authenticated()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/members/*/followers")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/members/*/followings")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/mypages/followerlists")).authenticated()

                        // websocket
                        .requestMatchers(antMatcher(HttpMethod.POST, "/chat/**")).permitAll()
                        .requestMatchers(antMatcher(HttpMethod.GET, "/chat/**")).permitAll()
                        .requestMatchers(antMatcher("/webjars/**")).permitAll()
                        .requestMatchers(antMatcher("/ws-stomp/**")).permitAll()
                        //.requestMatchers(antMatcher("/ws/chat")).permitAll()

                        //.anyRequest().authenticated()
                        .anyRequest().permitAll()
        );

        http.addFilterBefore(jwtAuthorizationFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}