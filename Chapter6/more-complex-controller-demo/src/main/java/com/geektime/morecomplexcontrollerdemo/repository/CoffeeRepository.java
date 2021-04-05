package com.geektime.morecomplexcontrollerdemo.repository;

import com.geektime.morecomplexcontrollerdemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

    Coffee findCoffeeByName(String name);
}
