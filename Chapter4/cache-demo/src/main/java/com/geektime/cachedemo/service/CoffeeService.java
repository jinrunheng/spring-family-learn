package com.geektime.cachedemo.service;

import com.geektime.cachedemo.mapper.CoffeeMapper;
import com.geektime.cachedemo.model.Coffee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "coffee")
public class CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Cacheable
    public List<Coffee> findAllCoffee() {
        return coffeeMapper.findAllCoffee();
    }

    @CacheEvict
    public void reloadCoffee() {
    }

}
