package com.bill.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)  {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(Object.class);
        // 设置键（key）的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 设置value序列化
        redisTemplate.setValueSerializer(serializer);
        // 设置HashKey序列化 为啥要hashkey
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置HashValue序列化
        redisTemplate.setHashValueSerializer(serializer);
        // 默认序列化
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}