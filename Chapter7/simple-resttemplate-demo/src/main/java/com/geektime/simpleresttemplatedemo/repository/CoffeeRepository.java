package com.geektime.simpleresttemplatedemo.repository;

import com.geektime.simpleresttemplatedemo.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
}
