package com.geektime.dockerdemo.repository;

import com.geektime.dockerdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Coffee findByName(String name);
}
