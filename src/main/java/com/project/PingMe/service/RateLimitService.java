package com.project.PingMe.service;

import com.project.PingMe.exception.RateLimitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RateLimitService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final int MAX_REQUESTS_PER_MINUTE = 10;

    public void checkRateLimit(String recipient) {
        // Make a Distinct key for each user
        String key = "rate_limit_notify:"+recipient;

        // Redis Will Automatically increment count
        //If the key is not there then it will set it to 1
        Long currentRequest = stringRedisTemplate.opsForValue().increment(key);

        //If it is a first request then set the timer to 1
        if(currentRequest != null && currentRequest == 1){
            stringRedisTemplate.expire(key, Duration.ofMinutes(1));
        }

        //Block if limit exceeded
        if(currentRequest != null && currentRequest > MAX_REQUESTS_PER_MINUTE){
            throw  new RateLimitException("Rate Limit Exceeded. Maximum " +  MAX_REQUESTS_PER_MINUTE + " requests per minute.");
        }
    }
}
