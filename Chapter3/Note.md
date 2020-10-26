## O/R Mapping 实践

#### 一：认识 Spring Data JPA

##### Jave Persistence API

JPA为对象关系映射提供了一种基于POJO的持久化模型

- 简化数据持久化代码的开发工作
- 为Java社区屏蔽不同持久化API的差异

JPA本身是一种规范，它的本质是一种ORM规范，所以只是提供了一些相关接口，但是接口并不能直接使用，JPA底层需要某种实现，Hibernate就是JPA接口的一种实现。



#### 二：定义JPA的实体对象

##### 常用JPA注解

实体

- @Entity,@MappedSuperclass
- @Table(name)

主键

- @Id
  - @GeneratedValue(strategy,generator)
  - @SequenceGenerator(name,squenceName)

映射

- @Column(name, nullable,length,insertable,updatable)
- @JoinTable(name),@JoinColumn(name)

关系

- @OneToOne,@OneToMany,@ManyToOne,@ManyToMany
- @OrderBy

##### Project Lombok

Project Lombok能够自动嵌入IDE和构建工具，提升开发效率

常用功能：

- @Getter/@Setter
- @ToString
- @NoArgsConstructor/@RequiredArgsConstructor/@AllArgsConstructor
- @Data
- @Builder
- @Slf4j/@CommonsLog/@Log4j

#### 三：咖啡馆实战项目：SpringBucks

##### Jpa-demo

Coffee

```java
package com.geektime.japdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.joda.money.Money;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_MENU") // 对应的表名
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Coffee implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // 在Hibernate中使用org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount类型映射Joda Money
    @Column
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

}

```



CoffeeOrder

```java
package com.geektime.japdemo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoffeeOrder implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    private String customer;

    // @ManyToMany是多对多关系映射
    // 多对多通常是通过建立一张中间表
    // @JoinTable 描述了多对多关系的数据表关系。
    @ManyToMany
    @JoinTable(name = "T_ORDER_COFFEE")
    private List<Coffee> items;

    @Column(nullable = false)
    private Integer state;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;


}

```



CoffeeOrderRepository

```java
package com.geektime.japdemo.repository;

import com.geektime.japdemo.model.CoffeeOrder;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeOrderRepository extends CrudRepository<CoffeeOrder, Long> {
}

```



CoffeeRepository

```java
package com.geektime.japdemo.repository;

import com.geektime.japdemo.model.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
```

JpaDemoApplication

```java
package com.geektime.japdemo;

import com.geektime.japdemo.model.Coffee;
import com.geektime.japdemo.model.CoffeeOrder;
import com.geektime.japdemo.repository.CoffeeOrderRepository;
import com.geektime.japdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class JapDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public static void main(String[] args) {
        SpringApplication.run(JapDemoApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
    }

    private void initOrders() {
        // 意式浓缩咖啡
        Coffee espresso = Coffee.builder()
                .name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.00))
                .build();
        coffeeRepository.save(espresso);
        log.info("Coffee:{}", espresso);

        // 拿铁
        Coffee latte = Coffee.builder()
                .name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.00))
                .build();

        coffeeRepository.save(latte);
        log.info("Coffee:{}", latte);

        // CoffeeOrder
        CoffeeOrder order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(0)
                .build();

        coffeeOrderRepository.save(order);
        log.info("Order:{}", order);

        order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Arrays.asList(espresso, latte))
                .state(1)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order:{}", order);
    }
}
```



##### Jpa-complex-demo

BaseEntity

```java
package com.geektime.jpacomplexdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;
}

```



Coffee

```java
package com.geektime.jpacomplexdemo.model;

import lombok.*;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "T_MENU")
@Builder
@Data
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Coffee extends BaseEntity implements Serializable {
    private String name;

    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmount",
            parameters = {@org.hibernate.annotations.Parameter(name = "currencyCode", value = "CNY")})
    private Money price;
}

```



CoffeeOrder

```java
package com.geektime.jpacomplexdemo.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "T_ORDER")
@Data
@Builder
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

```



OrderState

```java
package com.geektime.jpacomplexdemo.model;

public enum OrderState {
    INIT,PAID,BREWING,BREWED,TAKEN,CANCELLED
}

```



BaseRepository

```java
package com.geektime.jpacomplexdemo.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T,Long> extends PagingAndSortingRepository<T,Long> {
    List<T> findTop3ByOrderByUpdateTimeDescIdAsc();
}

```



CoffeeOrderRepository

```java
package com.geektime.jpacomplexdemo.repository;

import com.geektime.jpacomplexdemo.model.CoffeeOrder;

import java.util.List;

public interface CoffeeOrderRepository extends BaseRepository<CoffeeOrder, Long> {
    List<CoffeeOrder> findByCustomerOrderById(String customer);

    List<CoffeeOrder> findByItems_Name(String name);
}

```



CoffeeRepository

```java
package com.geektime.jpacomplexdemo.repository;

import com.geektime.jpacomplexdemo.model.Coffee;

public interface CoffeeRepository extends BaseRepository<Coffee, Long> {
}

```



JpaComplexDemoApplication

```java
package com.geektime.jpacomplexdemo;

import com.geektime.jpacomplexdemo.model.Coffee;
import com.geektime.jpacomplexdemo.model.CoffeeOrder;
import com.geektime.jpacomplexdemo.model.OrderState;
import com.geektime.jpacomplexdemo.repository.CoffeeOrderRepository;
import com.geektime.jpacomplexdemo.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@Slf4j
@EnableJpaRepositories
@EnableTransactionManagement
public class JpaComplexDemoApplication implements ApplicationRunner {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    private CoffeeOrderRepository coffeeOrderRepository;

    public static void main(String[] args) {
        SpringApplication.run(JpaComplexDemoApplication.class, args);
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        initOrders();
    }

    private void initOrders() {
        Coffee latte = Coffee.builder().name("latte")
                .price(Money.of(CurrencyUnit.of("CNY"), 30.0))
                .build();
        coffeeRepository.save(latte);
        log.info("Coffee: {}", latte);

        Coffee espresso = Coffee.builder().name("espresso")
                .price(Money.of(CurrencyUnit.of("CNY"), 20.0))
                .build();
        coffeeRepository.save(espresso);
        log.info("Coffee: {}", espresso);

        CoffeeOrder order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Collections.singletonList(espresso))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);

        order = CoffeeOrder.builder()
                .customer("Li Lei")
                .items(Arrays.asList(espresso, latte))
                .state(OrderState.INIT)
                .build();
        coffeeOrderRepository.save(order);
        log.info("Order: {}", order);
    }

    private void findOrders() {
        coffeeRepository.findAll(Sort.by(Sort.Direction.DESC, "id"))
                .forEach(coffee -> log.info("Loading:{}", coffee));

        List<CoffeeOrder> list = coffeeOrderRepository.findTop3ByOrderByUpdateTimeDescIdAsc();
        log.info("findTop3ByOrderByUpdateTimeDescIdAsc:{}", getJoinedOrderId(list));

        list = coffeeOrderRepository.findByCustomerOrderById("Li Lei");
        log.info("findByCustomerOrderByName:{}", getJoinedOrderId(list));

        // 不开启事务会因为没Session而报LazyInitializationException
        list.forEach(o -> {
            log.info("Order {}", o.getId());
            o.getItems().forEach(i -> log.info("  Item {}", i));
        });

        list = coffeeOrderRepository.findByItems_Name("latte");
        log.info("findByItems_Name: {}", getJoinedOrderId(list));
    }

    private String getJoinedOrderId(List<CoffeeOrder> list) {
        return list.stream().map(o -> o.getId().toString())
                .collect(Collectors.joining(","));
    }
}
```



#### 四：通过Spring Data JPA操作数据库

##### Repository

@EnableJpaRepositories

Repository<T,ID> 接口

- CrudRepository<T,ID>
- PagingAndSortingRepository<T,ID>
- JpaRepository<T,ID>

##### 定义查询

根据方法名定义查询

- find...By , read...By, query...By, get...By
- count...By
- ...OrderBy...[Asc / Desc]
- And / Or / IgnoreCase
- Top / First / Distinct

##### 分页查询

- PagingAndSortingRepository<T,ID>
- Pageable / Sort
- Slice<T> / Page<T>





