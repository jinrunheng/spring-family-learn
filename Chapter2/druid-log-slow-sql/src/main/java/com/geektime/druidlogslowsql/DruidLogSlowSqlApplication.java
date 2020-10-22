package com.geektime.druidlogslowsql;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@SpringBootApplication
@Slf4j
@EnableTransactionManagement(proxyTargetClass = true)
public class DruidLogSlowSqlApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(DruidLogSlowSqlApplication.class, args);
    }

    @Autowired
    private DataSource dataSource;

    @Autowired
    private FooService fooService;


    @Override
    public void run(String... args) throws Exception {
        log.info(dataSource.toString());
        new Thread(() -> fooService.selectForUpdate()).start();
        new Thread(() -> fooService.selectForUpdate()).start(); // 超时
    }
}
