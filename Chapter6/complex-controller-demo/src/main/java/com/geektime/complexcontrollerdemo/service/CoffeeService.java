package com.geektime.complexcontrollerdemo.service;

import com.geektime.complexcontrollerdemo.mapper.CoffeeMapper;
import com.geektime.complexcontrollerdemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeMapper.findAllCoffee();
    }

    public Coffee getCoffeeById(Long id) {
        return coffeeMapper.findCoffeeById(id);
    }

    public Coffee getCoffeeByName(String name) {
        return coffeeMapper.findCoffeeByName(name);
    }
}
