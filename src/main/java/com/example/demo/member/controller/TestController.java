package com.example.demo.member.controller;

import com.example.demo.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Profile("test")
@RestController
@RequiredArgsConstructor
public class TestController implements TestDocs {
    private final JwtUtil jwtUtil;

    @GetMapping("/test")
    public ResponseEntity<String> getAccessTokenForTest(
            @RequestParam String username
    ) {
        String token = jwtUtil.createTestToken(username);
        return ResponseEntity.ok(token);
    }
}
