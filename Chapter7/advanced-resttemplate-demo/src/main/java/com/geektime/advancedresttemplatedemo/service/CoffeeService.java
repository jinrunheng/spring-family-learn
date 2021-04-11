package com.geektime.advancedresttemplatedemo.service;

import com.geektime.advancedresttemplatedemo.model.Coffee;
import com.geektime.advancedresttemplatedemo.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    public Coffee getCoffeeById(Long id) {
        return coffeeRepository.getOne(id);
    }

    public Coffee getCoffeeByName(String name) {
        return coffeeRepository.findByName(name);
    }

    public Coffee saveCoffee(Coffee newCoffee) {
        return coffeeRepository.save(newCoffee);
    }

    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll();
    }
}
