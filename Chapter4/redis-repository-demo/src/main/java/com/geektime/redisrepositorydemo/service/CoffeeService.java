package com.geektime.redisrepositorydemo.service;

import com.geektime.redisrepositorydemo.mapper.CoffeeMapper;
import com.geektime.redisrepositorydemo.model.Coffee;
import com.geektime.redisrepositorydemo.model.CoffeeCache;
import com.geektime.redisrepositorydemo.repository.CoffeeCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CoffeeService {

    @Autowired
    private CoffeeMapper coffeeMapper;

    @Autowired
    private CoffeeCacheRepository coffeeCacheRepository;

    public List<Coffee> findAllCoffee() {
        return coffeeMapper.findAllCoffee();
    }

    public Coffee findCoffee(String name) {
        Coffee coffee = coffeeMapper.findCoffeeByName(name);
        log.info("Coffee Found: {}", coffee);
        return coffee;
    }

    public Coffee findCoffeeFromCache(String name) {

        Optional<CoffeeCache> cached = coffeeCacheRepository.findCoffeeByName(name);

        if (cached.isPresent()) {
            CoffeeCache coffeeCache = cached.get();
            Coffee coffee = Coffee.builder()
                    .name(coffeeCache.getName())
                    .price(coffeeCache.getPrice())
                    .build();
            log.info("Coffee {} found in cache.", coffeeCache);
            return coffee;
        } else {
            Coffee coffee = findCoffee(name);
            CoffeeCache coffeeCache = CoffeeCache.builder()
                    .id(coffee.getId())
                    .name(coffee.getName())
                    .price(coffee.getPrice())
                    .build();
            log.info("Save Coffee {} to cache.", coffeeCache);
            coffeeCacheRepository.save(coffeeCache);

            return coffee;
        }
    }
}
