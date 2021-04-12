package com.geektime.hateoaswaiterservice.repository;

import com.geektime.hateoaswaiterservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
