package com.geektime.thymeleafviewdemo.controller;

import com.geektime.thymeleafviewdemo.controller.request.NewCoffeeRequest;
import com.geektime.thymeleafviewdemo.model.Coffee;
import com.geektime.thymeleafviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(path = "/"
            , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Binding Errors : {}", result);
            return null;
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @GetMapping(path = "/all")
    @ResponseBody
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseBody
    public Coffee getCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        return coffee;
    }


    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Coffee getCoffeeByName(@RequestParam String name) {
        return coffeeService.getCoffeeByName(name);
    }
}
