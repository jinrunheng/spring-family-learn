package com.geektime.redisdemo.service;

import com.geektime.redisdemo.mapper.CoffeeMapper;
import com.geektime.redisdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class CoffeeService {

    private static final String CACHE = "coffee";

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private RedisTemplate<String, Coffee> redisTemplate;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.findAllCoffee();
    }

    public Optional<Coffee> findOneCoffee(String name) {
        HashOperations<String, String, Coffee> hashOperations = redisTemplate.opsForHash();
        if (redisTemplate.hasKey(CACHE) && hashOperations.hasKey(CACHE, name)) {
            log.info("Get coffee {} from Redis.", name);
            return Optional.of(hashOperations.get(CACHE, name));
        }else {
            Coffee coffee = coffeeMapper.findByName(name);
            log.info("Coffee Found: {}", coffee);
            log.info("Put coffee {} to Redis.", name);
            hashOperations.put(CACHE, name, coffee);
            redisTemplate.expire(CACHE, 1, TimeUnit.MINUTES); // 设置过期时间为 1min
            return Optional.of(coffee);
        }
    }
}
