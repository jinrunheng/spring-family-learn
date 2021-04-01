package com.geektime.simplecontrollerdemo.controller;

import com.geektime.simplecontrollerdemo.model.Coffee;
import com.geektime.simplecontrollerdemo.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/all")
    @ResponseBody
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

    @GetMapping("/{name}")
    @ResponseBody
    public Coffee getCoffeeByName(@PathVariable String name) {
        return coffeeService.getCoffeeByName(name);
    }
}
