package com.geektime.complexresttemplatedemo.repository;

import com.geektime.complexresttemplatedemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    Coffee findByName(String name);
}
