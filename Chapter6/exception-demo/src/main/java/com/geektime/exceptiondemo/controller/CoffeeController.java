package com.geektime.exceptiondemo.controller;


import com.geektime.exceptiondemo.controller.exception.FormValidationException;
import com.geektime.exceptiondemo.controller.request.NewCoffeeRequest;
import com.geektime.exceptiondemo.model.Coffee;
import com.geektime.exceptiondemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
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
            throw new FormValidationException(result);
        }
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffee(@Valid @RequestBody NewCoffeeRequest newCoffee, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Binding Errors : {}", result);
            throw new ValidationException(result.toString());
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
