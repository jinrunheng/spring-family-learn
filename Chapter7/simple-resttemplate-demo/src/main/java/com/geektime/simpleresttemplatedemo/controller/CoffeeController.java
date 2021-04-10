package com.geektime.simpleresttemplatedemo.controller;

import com.geektime.simpleresttemplatedemo.model.Coffee;
import com.geektime.simpleresttemplatedemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        log.info("Coffee : {}", coffee);
        return coffee;
    }

    @PostMapping(value = "/")
    public Coffee addCoffee(@RequestBody Coffee newCoffee) {
        Coffee coffee = Coffee.builder().name(newCoffee.getName()).price(newCoffee.getPrice()).build();
        return coffeeService.saveCoffee(coffee);
    }

    @GetMapping("/")
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

}
