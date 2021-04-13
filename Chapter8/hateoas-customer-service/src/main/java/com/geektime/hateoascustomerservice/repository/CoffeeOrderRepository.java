package com.geektime.hateoascustomerservice.repository;


import com.geektime.hateoascustomerservice.model.CoffeeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoffeeOrderRepository extends JpaRepository<CoffeeOrder, Long> {
}
