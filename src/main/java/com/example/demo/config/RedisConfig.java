//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.ChannelTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@EnableCaching
//@Configuration
//public class RedisConfig {
//
//    @Value("${spring.data.redis.host}")
//    private String host;
//
//    @Value("${spring.data.redis.port}")
//    private int port;
//
////    @Value("${spring.data.redis.password}")
////    public String password;
//
////    @Bean
////    public RedisConnectionFactory redisConnectionFactory() {
////        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
////        configuration.setHostName(host);
////        configuration.setPassword(password);
////        //password 설정을 추가합니다.
////        configuration.setPort(port);
////        return new LettuceConnectionFactory(configuration);
////    }
////
////    @Bean
////    public RedisTemplate<?, ?> redisTemplate() {
////        RedisTemplate<?, ?> redisTemplate = new RedisTemplate<>();
////        redisTemplate.setConnectionFactory(redisConnectionFactory());
////        return redisTemplate;
////    }
//}