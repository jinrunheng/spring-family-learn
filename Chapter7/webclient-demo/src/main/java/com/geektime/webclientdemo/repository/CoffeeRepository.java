package com.geektime.webclientdemo.repository;

import com.geektime.webclientdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
