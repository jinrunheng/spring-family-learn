package com.geektime.jsonviewdemo.repository;

import com.geektime.jsonviewdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Coffee findByName(String name);
}