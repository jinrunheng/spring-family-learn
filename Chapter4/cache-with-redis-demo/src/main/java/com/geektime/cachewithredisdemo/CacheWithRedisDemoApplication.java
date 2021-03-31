package com.geektime.cachewithredisdemo;

import com.geektime.cachewithredisdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@Slf4j
@EnableCaching(proxyTargetClass = true)
public class CacheWithRedisDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeService coffeeService;

	public static void main(String[] args) {
		SpringApplication.run(CacheWithRedisDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Count: {}", coffeeService.findAllCoffee().size());
		for (int i = 0; i < 5; i++) {
			log.info("Reading from cache.");
			coffeeService.findAllCoffee();
		}
		Thread.sleep(5000); // 我们设置了缓存的失效为 5s
		log.info("Reading after refresh.");
		coffeeService.findAllCoffee().forEach(c -> log.info("Coffee {}", c.getName()));
	}
}
