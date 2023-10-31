package com.example.demo;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.security.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@Slf4j
@TestPropertySource(
        properties = {
                "PROFILE=local",
                "MYSQL_ROOT_PASSWORD=1234"
        }
)

@SpringBootTest
@ContextConfiguration(classes = MainProjectApplication.class)
public class JwtMaker {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    void make() {
        String username = "test1";
        String token = jwtUtil.createToken(username, UserRoleEnum.USER);
        log.info(token);
    }
}