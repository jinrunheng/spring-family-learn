package com.geektime.redisrepositorydemo;

import com.geektime.redisrepositorydemo.converter.BytesToMoneyConverter;
import com.geektime.redisrepositorydemo.converter.MoneyToBytesConverter;
import com.geektime.redisrepositorydemo.model.Coffee;
import com.geektime.redisrepositorydemo.service.CoffeeService;
import io.lettuce.core.ReadFrom;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.Arrays;

@Slf4j
@SpringBootApplication
@MapperScan("com.geektime.redisrepositorydemo.mapper")
@EnableRedisRepositories
public class RedisRepositoryDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeService coffeeService;


    @Bean
    public LettuceClientConfigurationBuilderCustomizer customizer() {
        return builder -> builder.readFrom(ReadFrom.MASTER_PREFERRED);
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(
                Arrays.asList(new MoneyToBytesConverter(), new BytesToMoneyConverter()));
    }

    public static void main(String[] args) {
        SpringApplication.run(RedisRepositoryDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Coffee c = coffeeService.findCoffee("mocha");
        log.info("Coffee {}", c);

        for (int i = 0; i < 5; i++) {
            c = coffeeService.findCoffeeFromCache("mocha");
        }

        log.info("Value from Redis: {}", c);
    }
}
