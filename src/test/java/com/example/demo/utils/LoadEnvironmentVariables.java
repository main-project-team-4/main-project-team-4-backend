package com.example.demo.utils;

import com.example.demo.config.TestRedisConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@TestPropertySource(locations = "classpath:.env")
@Import({TestRedisConfig.class})
public @interface LoadEnvironmentVariables {
}
