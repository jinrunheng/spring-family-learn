package com.geektime.simplecontrollerdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.geektime.simplecontrollerdemo.mapper")
public class SimpleControllerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleControllerDemoApplication.class, args);
	}

}
