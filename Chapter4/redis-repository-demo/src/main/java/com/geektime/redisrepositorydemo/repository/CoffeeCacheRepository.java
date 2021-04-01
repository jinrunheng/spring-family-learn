package com.geektime.redisrepositorydemo.repository;

import com.geektime.redisrepositorydemo.model.CoffeeCache;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CoffeeCacheRepository extends CrudRepository<CoffeeCache,Long> {
    Optional<CoffeeCache> findCoffeeByName(String name);
}
