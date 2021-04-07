package com.geektime.thymeleafviewdemo.repository;

import com.geektime.thymeleafviewdemo.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
