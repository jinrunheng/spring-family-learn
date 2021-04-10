package com.geektime.simpleresttemplatedemo.service;

import com.geektime.simpleresttemplatedemo.model.Coffee;
import com.geektime.simpleresttemplatedemo.repository.CoffeeRepository;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.getOne(id);
    }

    public Coffee saveCoffee(Coffee newCoffee) {
        return coffeeRepository.save(newCoffee);
    }

    public List<Coffee> getAllCoffee(){
        return coffeeRepository.findAll(Sort.by("id"));
    }
}
