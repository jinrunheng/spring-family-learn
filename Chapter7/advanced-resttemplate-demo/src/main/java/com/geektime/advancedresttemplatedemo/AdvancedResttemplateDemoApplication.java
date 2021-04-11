package com.geektime.advancedresttemplatedemo;

import com.geektime.advancedresttemplatedemo.model.Coffee;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.TimeZone;

@SpringBootApplication
@Slf4j
public class AdvancedResttemplateDemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(AdvancedResttemplateDemoApplication.class, args);
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
                .fromUriString("http://localhost:8080/coffee/?name={name}")
                .build("mocha");

        RequestEntity<Void> requestEntity = RequestEntity.get(uri)
                .build();

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
        log.info("Response Status : {}", responseEntity.getStatusCode());
        log.info("Response Headers : {}", responseEntity.getHeaders().toString());


        String coffeeUri = "http://localhost:8080/coffee/";
        Coffee request = Coffee.builder()
                .name("Americano")
                .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                .build();

        Coffee response = restTemplate.postForObject(coffeeUri, request, Coffee.class);
        log.info("New Coffee : {}", response);

        ParameterizedTypeReference<List<Coffee>> ptr =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<Coffee>> list = restTemplate
                .exchange(coffeeUri, HttpMethod.GET, null, ptr);
        list.getBody().forEach(c -> log.info("Coffee : {}", c));
    }
}
