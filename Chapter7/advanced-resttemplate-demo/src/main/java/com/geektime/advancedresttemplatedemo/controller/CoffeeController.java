package com.geektime.advancedresttemplatedemo.controller;

import com.geektime.advancedresttemplatedemo.model.Coffee;
import com.geektime.advancedresttemplatedemo.service.CoffeeService;
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

    @GetMapping(path = "/",
            params = "name")
    public Coffee getCoffeeByName(@RequestParam String name) {
        return coffeeService.getCoffeeByName(name);
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
