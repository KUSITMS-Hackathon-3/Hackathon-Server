package com.example.hackathon.global.config.security.redis;

//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;

//import java.time.Duration;
//import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
//@RequiredArgsConstructor
public class RedisRepository {
//    private final RedisTemplate<String, String> redisTemplate;
//
//    public void setValue(String key, String data) {
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        values.set(key, data);
//    }
//
//    public void setValues(String key, String data, Duration duration) {
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        values.set(key, data, duration);
//    }
//
//    public Optional<String> getValues(String key) {
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        return Optional.ofNullable(values.get(key));
//    }
//
//    public void deleteValues(String key) {
//        redisTemplate.delete(key);
//    }
}
