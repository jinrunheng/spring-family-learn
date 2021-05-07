package com.geektime.http2waiterservice.controller;

import com.geektime.http2waiterservice.controller.request.NewOrderRequest;
import com.geektime.http2waiterservice.model.Coffee;
import com.geektime.http2waiterservice.model.CoffeeOrder;
import com.geektime.http2waiterservice.service.CoffeeOrderService;
import com.geektime.http2waiterservice.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@Slf4j
public class CoffeeOrderController {

    @Autowired
    private CoffeeOrderService orderService;

    @Autowired
    private CoffeeService coffeeService;

    @GetMapping("/{id}")
    public CoffeeOrder getOrder(@PathVariable Long id) {
        CoffeeOrder order = orderService.get(id);
        log.info("Get Order : {}", order);
        return order;
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public CoffeeOrder create(@RequestBody NewOrderRequest newOrder) {
        log.info("Receive new order : {}", newOrder);
        Coffee[] coffees = coffeeService.getCoffeeByList(newOrder.getItems()).toArray(new Coffee[]{});
        return orderService.createOrder(newOrder.getCustomer(), coffees);
    }

}
