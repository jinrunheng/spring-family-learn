package com.geekitime.staticcachedemo.controller.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
public class NewOrderRequest {
    @NotNull
    private String customer;
    @NotNull
    private List<String> items;
}
