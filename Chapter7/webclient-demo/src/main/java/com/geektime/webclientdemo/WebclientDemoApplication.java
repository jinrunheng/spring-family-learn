package com.geektime.webclientdemo;

import com.geektime.webclientdemo.model.Coffee;
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
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@Slf4j
public class WebclientDemoApplication implements ApplicationRunner {


    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl("http://localhost:8080").build();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.indentOutput(true);
            builder.timeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        };
    }

    @Autowired
    public WebClient webClient;

    public static void main(String[] args) {
        SpringApplication.run(WebclientDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        CountDownLatch cdl = new CountDownLatch(2);

        webClient.get()
                .uri("/coffee/{id}", 1)
                .retrieve()
                .bodyToMono(Coffee.class)
                .doOnError(e -> log.error("Error : {}", e))
                .doFinally(s -> cdl.countDown())
                .subscribeOn(Schedulers.single())
                .subscribe(c -> log.info("Coffee 1 : {}", c));

        Mono<Coffee> americano = Mono.just(
                Coffee.builder()
                        .name("americano")
                        .price(Money.of(CurrencyUnit.of("CNY"), 25.00))
                        .build()
        );
        webClient.post()
                .uri("/coffee/")
                .body(americano, Coffee.class)
                .retrieve()
                .bodyToMono(Coffee.class)
                .doFinally(s -> cdl.countDown())
                .subscribeOn(Schedulers.single())
                .subscribe(c -> log.info("Coffee Created : {}", c));

        cdl.await();


        webClient.get()
                .uri("/coffee/")
                .retrieve()
                .bodyToFlux(Coffee.class)
                .toStream()
                .forEach(c -> log.info("Coffee in List : {}", c));
    }
}
