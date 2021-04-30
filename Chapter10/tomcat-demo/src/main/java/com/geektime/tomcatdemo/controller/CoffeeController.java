package com.geektime.tomcatdemo.controller;

import com.geektime.tomcatdemo.controller.request.NewCoffeeRequest;
import com.geektime.tomcatdemo.model.Coffee;
import com.geektime.tomcatdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @GetMapping(path = "/all")
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping(path = "/{id}")
    public Coffee getCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        log.info("Coffee : {}", coffee);
        return coffee;
    }

    @GetMapping(path = "/", params = "name")
    public Coffee getCoffeeByName(@RequestParam String name) {
        return coffeeService.getCoffeeByName(name);
    }
}
