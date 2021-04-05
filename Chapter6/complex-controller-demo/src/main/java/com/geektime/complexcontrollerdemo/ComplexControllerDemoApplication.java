package com.geektime.complexcontrollerdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.geektime.complexcontrollerdemo.mapper")
public class ComplexControllerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ComplexControllerDemoApplication.class, args);
    }

}
