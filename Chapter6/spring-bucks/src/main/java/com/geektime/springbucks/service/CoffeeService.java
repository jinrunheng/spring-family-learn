package com.geektime.springbucks.service;

import com.geektime.springbucks.model.Coffee;
import com.geektime.springbucks.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
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
    private CoffeeRepository coffeeRepository;

    public Coffee saveCoffee(String name, Money price) {
        return coffeeRepository.save(Coffee.builder().name(name).price(price).build());
    }

    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll();
    }

    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.getOne(id);
    }

    public Coffee getCoffeeByName(String name) {
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByNames(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}

