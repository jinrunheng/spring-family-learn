package com.geektime.exceptiondemo.repository;

import com.geektime.exceptiondemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);

    List<Coffee> findByNameInOrderById(List<String> list);
}
