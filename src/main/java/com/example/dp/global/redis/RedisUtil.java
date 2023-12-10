package com.example.dp.global.redis;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> blackList;
    private final RedisTemplate<String, Object> mailList;

    public void set(String key, Object o, int minutes) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void addBlackList(String key, Object o, int minutes) {
        blackList.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        blackList.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public boolean containBlackList(String key) {
        return Boolean.TRUE.equals(blackList.hasKey(key));
    }

    public void addMailList(String key, Object o, int minutes) {
        mailList.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        mailList.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object getCode(String key) {
        return mailList.opsForValue().get(key);
    }

    public Boolean hasMail(String key) {
        return mailList.hasKey(key);
    }
    public void deleteMail(String key) {
        mailList.delete(key);
    }
}