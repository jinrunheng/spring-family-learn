package com.geektime.japdemo.repository;

import com.geektime.japdemo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
