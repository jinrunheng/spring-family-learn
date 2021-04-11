package com.geektime.webclientdemo.controller;

import com.geektime.webclientdemo.model.Coffee;
import com.geektime.webclientdemo.service.CoffeeService;
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

    @GetMapping(path = "/{id}")
    public Coffee getCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        log.info("Coffee : {}", coffee);
        return coffee;
    }

    @PostMapping(path = "/")
    public Coffee addCoffee(@RequestBody Coffee newCoffee) {
        return coffeeService.saveCoffee(newCoffee);
    }

    @GetMapping(path = "/")
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

}