package com.geekitime.staticcachedemo.service;

import com.geekitime.staticcachedemo.model.Coffee;
import com.geekitime.staticcachedemo.model.CoffeeOrder;
import com.geekitime.staticcachedemo.model.OrderState;
import com.geekitime.staticcachedemo.repository.CoffeeOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;

@Service
@Slf4j
@Transactional
public class CoffeeOrderService {

    @Autowired
    private CoffeeOrderRepository orderRepository;

    public CoffeeOrder get(Long id) {
        return orderRepository.getOne(id);
    }

    public CoffeeOrder createOrder(String customer, Coffee[] coffees) {
        CoffeeOrder order = CoffeeOrder.builder()
                .customer(customer)
                .items(new ArrayList<>(Arrays.asList(coffees)))
                .state(OrderState.INIT)
                .build();

        CoffeeOrder saved = orderRepository.save(order);
        log.info("New Order : {}", saved);
        return saved;
    }

    public boolean updateState(CoffeeOrder order, OrderState state) {
        if (state.compareTo(order.getState()) <= 0) {
            return false;
        }
        order.setState(state);
        orderRepository.save(order);
        log.info("Updated Order : {}", order);
        return true;
    }
}
