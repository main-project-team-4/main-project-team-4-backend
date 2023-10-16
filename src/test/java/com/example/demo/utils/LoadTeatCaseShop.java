package com.example.demo.utils;

import org.springframework.test.context.jdbc.Sql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Sql(
        scripts = {"classpath:testcase-shop.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public @interface LoadTeatCaseShop {
}
