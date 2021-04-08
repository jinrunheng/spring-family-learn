package com.geektime.exceptiondemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories
public class ExceptionDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExceptionDemoApplication.class, args);
    }

}
