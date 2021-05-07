package com.geektime.http2waiterservice.service;

import com.geektime.http2waiterservice.model.Coffee;
import com.geektime.http2waiterservice.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
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
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.getOne(id);
    }

    public Long getCoffeeCount() {
        return coffeeRepository.count();
    }

    public Coffee getCoffeeByName(String name) {
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByList(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }
}

