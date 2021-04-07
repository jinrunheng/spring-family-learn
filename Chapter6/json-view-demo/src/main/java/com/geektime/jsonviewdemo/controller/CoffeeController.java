package com.geektime.jsonviewdemo.controller;

import com.geektime.jsonviewdemo.controller.request.NewCoffeeRequest;
import com.geektime.jsonviewdemo.model.Coffee;
import com.geektime.jsonviewdemo.service.CoffeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {

    @Autowired
    private CoffeeService coffeeService;

    @PostMapping(path = "/",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addCoffeeWithoutBindingResult(@Valid NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }


    @PostMapping(path = "/",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        coffees.add(coffeeService.saveCoffee(arr[0], Money.of(CurrencyUnit.of("CNY"), NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }

    @GetMapping(path = "/all", params = "!name")
    @ResponseBody
    public List<Coffee> getAllCoffee() {
        return coffeeService.getAllCoffee();
    }

    @RequestMapping(path = "/{id}",
            method = RequestMethod.GET)
    @ResponseBody
    public Coffee getCoffeeById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        log.info("Coffee : {}",coffee);
        return coffee;
    }

    @GetMapping(path = "/", params = "name")
    @ResponseBody
    public Coffee getCoffeeByName(@RequestParam String name) {
        return coffeeService.getCoffeeByName(name);
    }
}
