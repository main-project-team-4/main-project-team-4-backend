package com.example.demo.utils.testcase;

import com.example.demo.jwt.JwtUtil;
import com.example.demo.security.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Component
public class AuthTestUtil {
        @Autowired
        private JwtUtil jwtUtil;

        @Retention(RetentionPolicy.RUNTIME)
        @SqlGroup({
                @Sql("classpath:data/testcase-auth.sql"),
                @Sql(
                        scripts = "classpath:truncate-testcases.sql",
                        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD
                )
        })
        public @interface LoadTestCaseAuth {}

        public String getToken(String username) {
                return jwtUtil.createToken(username, UserRoleEnum.USER);
        }

        public MockHttpServletRequestBuilder setAccessToken(MockHttpServletRequestBuilder builder, String username) {
                return builder.header(HttpHeaders.AUTHORIZATION, getToken(username));
        }

        public MockHttpServletRequestBuilder setAccessToken(MockHttpServletRequestBuilder builder) {
                return setAccessToken(builder, "1");
        }
}


