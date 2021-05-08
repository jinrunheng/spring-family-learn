package com.geektime.commandlinedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CommandLineDemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CommandLineDemoApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);
        // 根据 application.properties 里的配置来决定 WebApplicationType
        // SpringApplication.run(CommandLineDemoApplication.class, args);
    }

}
