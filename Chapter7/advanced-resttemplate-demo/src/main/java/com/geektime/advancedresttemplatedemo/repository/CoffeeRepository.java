package com.geektime.advancedresttemplatedemo.repository;

import com.geektime.advancedresttemplatedemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);
}
