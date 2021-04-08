package com.geekitime.staticcachedemo.controller;

import com.geekitime.staticcachedemo.controller.request.NewOrderRequest;
import com.geekitime.staticcachedemo.model.Coffee;
import com.geekitime.staticcachedemo.model.CoffeeOrder;
import com.geekitime.staticcachedemo.service.CoffeeOrderService;
import com.geekitime.staticcachedemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService coffeeOrderService;

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable("id") Long id) {
        return coffeeOrderService.get(id);
    }

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new Order {}", newOrder);
        List<Coffee> list = coffeeService.getCoffeeByList(newOrder.getItems());
        Coffee[] coffees = list.toArray(new Coffee[]{});
        return coffeeOrderService.createOrder(newOrder.getCustomer(), coffees);
    }
}
