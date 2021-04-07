package com.geektime.thymeleafviewdemo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

// 一个订单可以对应多个咖啡，一种咖啡可以出现在多个订单上，是多对多的关系
@Entity
@Table(name = "T_ORDER")
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CoffeeOrder extends BaseEntity implements Serializable {
    private String customer;

    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    @OrderBy("id")
    private List<Coffee> items;

    @Enumerated
    @Column(nullable = false)
    private OrderState state;
}
