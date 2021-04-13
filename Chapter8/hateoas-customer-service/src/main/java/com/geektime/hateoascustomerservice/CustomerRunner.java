package com.geektime.hateoascustomerservice;

import com.geektime.hateoascustomerservice.model.Coffee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@Slf4j
public class CustomerRunner implements ApplicationRunner {

    private static final URI ROOT_URI = URI.create("http://localhost:8080/");

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Link coffeeLink = getLink(ROOT_URI, "coffees");
        readCoffeeMenu(coffeeLink);
    }

    private void readCoffeeMenu(Link coffeeLink) {
        ResponseEntity<PagedResources<Resource<Coffee>>> coffeeResp =
                restTemplate.exchange(coffeeLink.getTemplate().expand(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<PagedResources<Resource<Coffee>>>() {
                        });

        log.info("Menu Response : {}", coffeeResp.getBody());
    }

    private Link getLink(URI uri, String rel) {
        ResponseEntity<Resources<Link>> rootResp =
                restTemplate.exchange(uri, HttpMethod.GET, null,
                        new ParameterizedTypeReference<Resources<Link>>() {
                        });

        Link link = rootResp.getBody().getLink(rel);
        log.info("Link : {}", link);
        return link;
    }
}
