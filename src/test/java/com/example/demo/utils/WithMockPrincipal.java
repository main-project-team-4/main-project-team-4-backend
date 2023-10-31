package com.example.demo.utils;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockPrincipalSecurityContextFactory.class)
public @interface WithMockPrincipal {
    String username() default "iksadnorth";
    String password() default "q1w2e3r4";
    String nickname() default "iksad";
}
