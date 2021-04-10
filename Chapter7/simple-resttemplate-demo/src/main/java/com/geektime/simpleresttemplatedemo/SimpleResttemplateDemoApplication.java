package com.geektime.simpleresttemplatedemo;

import com.geektime.simpleresttemplatedemo.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class SimpleResttemplateDemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SimpleResttemplateDemoApplication.class, args);
    }

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.indentOutput(true);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        URI uri = UriComponentsBuilder
                .fromUriString("http://localhost:8080/coffee/{id}").build(1);

        log.info(uri.toString());
        ResponseEntity<Coffee> c = restTemplate.getForEntity(uri, Coffee.class);
        log.info("Response Status: {}", c.getStatusCode());
        log.info("Response Headers: {}", c.getHeaders().toString());
        log.info("Coffee: {}", c.getBody());


        String coffeeUri = "http://localhost:8080/coffee/";
        Coffee newCoffee = Coffee.builder()
                .name("Americano")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                .build();

        Coffee response = restTemplate.postForObject(coffeeUri, newCoffee, Coffee.class);
        log.info("New Coffee saved: {}", response);
        String s = restTemplate.getForObject(coffeeUri, String.class);
        log.info("String Coffee : {}", s);
    }
}
