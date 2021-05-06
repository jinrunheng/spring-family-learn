package com.geektime.sslwaiterservice.support;

import com.geektime.sslwaiterservice.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CoffeeIndicator implements HealthIndicator {

    @Autowired
    private CoffeeService coffeeService;

    @Override
    public Health health() {
        long count = coffeeService.getCoffeeCount();
        Health health = null;
        if (count > 0) {
            health = Health.up()
                    .withDetail("count", count)
                    .withDetail("message", "We have enough coffee.")
                    .build();
        } else {
            health = Health.down()
                    .withDetail("count", 0)
                    .withDetail("message", "We are out of Coffee.")
                    .build();
        }
        return health;
    }
}
