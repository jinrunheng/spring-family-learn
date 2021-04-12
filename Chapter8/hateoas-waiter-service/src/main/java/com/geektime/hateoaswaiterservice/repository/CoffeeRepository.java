package com.geektime.hateoaswaiterservice.repository;

import com.geektime.hateoaswaiterservice.model.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "/coffee")
public interface CoffeeRepository extends JpaRepository<Coffee, Long> {
    List<Coffee> findByNameInOrderById(List<String> names);

    Coffee findByName(String name);
}
