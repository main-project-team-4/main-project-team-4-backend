package com.example.demo.chat.redis;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import redis.embedded.RedisServer;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Objects;

@Profile("local")
@Configuration
public class ChatRedisLocalConfig {
    @Value("${spring.data.redis.port}")
    private int redisPort = 6379;

    private RedisServer redisServer;

    @PostConstruct
    public void start() {
        if (isArmArchitecture()) {
            redisServer = new RedisServer(Objects.requireNonNull(getRedisServerExecutable()),
                    redisPort);
        }
        if (!isArmArchitecture()) {
            redisServer = RedisServer.builder()
                    .port(redisPort)
                    .setting("maxmemory 128M")
                    .build();
        }
        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private File getRedisServerExecutable() {
        try {
            return new File("src/main/resources/binary/redis/redis-server-7.2.2-mac-arm64");
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isArmArchitecture() {
        return System.getProperty("os.arch").contains("aarch64");
    }
}
