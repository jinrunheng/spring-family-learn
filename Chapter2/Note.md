## JDBC 必知必会

### 如何配置单数据源

项目演示：

首先，使用Spring Initializr添加相关依赖，生成项目骨架

添加的依赖有

1. Actuator
2. H2
3. JDBC
4. Lombok(需要在IDEA下载好Lombok的插件)
5. Spring Web



#### 我们可以自己直接配置所需的Bean

数据源相关

-  DataSource(根据选择的链接池实现决定)

事务相关（可选）

- PlatormTransactionManager (DataSoureTransactionManager)
- TransactionTemplate

操作相关（可选）

- JdbcTemplate



#### 示例程序

该程序为在日志中打印DataSource,Connection,以及数据通过SpringBoot替我们配置好的JdbcTemplate操作数据库

Application.properties

```properties
# 否则无法访问actuator的 http://localhost:8080/actuator/beans
management.endpoints.web.exposure.include=*

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=root
spring.datasource.password=123456
```



在SpringBoot中，resources下的schema.sql与data.sql可以被直接执行

schema.sql

```sql
CREATE TABLE FOO (ID INT IDENTITY,BAR VARCHAR(64));
```

data.sql

```sql
INSERT INTO FOO (ID,BAR) VALUES (1,'aaa');
INSERT INTO FOO (ID,BAR) VALUES (2,'bbb');
```

DatasourceDemoApplication

```java
package com.geektime.datasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class DatasourceDemoApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        Connection connection = dataSource.getConnection();
        log.info(connection.toString());
        connection.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("SELECT * FROM FOO")
                .forEach(message -> log.info(message.toString()));
    }
}
```



@Slf4j注解是lombok包下的，可以直接调用log对象打印日志；当实现了CommandLineRunner接口后，就可以在项目启动后执行某些功能，实现功能的代码放在实现的run方法中

日志信息中，包含了我们想要打印的Datasource,Connection与查询后的data：

```reStructuredText
2020-10-21 01:40:37.955  INFO 3383 --- [           main] c.g.d.DatasourceDemoApplication          : HikariDataSource (HikariPool-1)
2020-10-21 01:40:37.955  INFO 3383 --- [           main] c.g.d.DatasourceDemoApplication          : HikariProxyConnection@1476985549 wrapping conn0: url=jdbc:h2:mem:testdb user=ROOT
2020-10-21 01:40:37.969  INFO 3383 --- [           main] c.g.d.DatasourceDemoApplication          : {ID=1, BAR=aaa}
2020-10-21 01:40:37.969  INFO 3383 --- [           main] c.g.d.DatasourceDemoApplication          : {ID=2, BAR=bbb}
```



#### Spring Boot替我们做了哪些配置？

DataSourceAutoConfiguration

- 配置DataSource

DataSourceTransactionManagerAutoConfiguration

- 配置DataSourceTransactionManager

JdbcTemplateAutoConfiguration

- 配置JdbcTemplate

当符合条件的时候，才会进行配置，自己已经手动配置的部分，SpringBoot则不会帮助我们配置

#### 数据源相关配置属性

通用

- spring.datasource.url=jdbc:mysql://localhost/test
- spring.datasource.username=root
- spring.datasource.password=123456
- spring.datasource.driver-class-name=com.mysql.jdbc.Driver(可选)

初始化内嵌数据库

- spring.datasource.initialization-mode=embedded|always|never
- spring.datasource.schema与spring.datasource.data确定初始化的SQL文件
- spring.datasource.platform=h2|oracle|mysql|postgresql（与前者对应）



### 如何配置多数据源

不同数据源的配置要分开

关注每次使用的数据源

- 由多个DataSource时系统如何判断
- 对应的设施（事务，ORM等等）如何选择DataSource



#### Spring Boot中的多数据源配置

手工配置两组DataSource及相关内容

与Spring Boot协同工作（二选一）

- 配置@Primary类型的Bean
- 排除Spring Boot的自动配置
  - DataSourceAutoConfiguration
  - DataSourceTransactionManagerAutoConfiguration
  - JdbcTemplateAutoConfiguration

##### 演示示例

application.properties

```properties
management.endpoints.web.exposure.include=*

foo.datasource.url=jdbc:h2:mem:foo
foo.datasource.username=root1
foo.datasource.password=123456

bar.datasource.url=jdbc:h2:mem:bar
bar.datasource.username=root2
bar.datasource.password=123456
```



上述配置中，我们自己定义了数据源

MultiDatasourceDemoApplication

```java
package com.geektime.multidatasourcedemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class
})
@Slf4j
public class MultiDatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultiDatasourceDemoApplication.class, args);
    }

    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties fooDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource fooDataSource() {
        DataSourceProperties dataSourceProperties = fooDataSourceProperties();
        log.info("foo datasource:{}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager fooTxManager(DataSource fooDataSource) {
        return new DataSourceTransactionManager(fooDataSource);
    }

    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties barDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource barDataSource() {
        DataSourceProperties dataSourceProperties = barDataSourceProperties();
        log.info("bar datasource:{}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    @Resource
    public PlatformTransactionManager barTxManager(DataSource barDataSource) {
        return new DataSourceTransactionManager(barDataSource);
    }
}
```



打印日志结果中，含有数据源信息：

```reStructuredText
2020-10-21 02:55:46.520  INFO 3688 --- [           main] c.g.m.MultiDatasourceDemoApplication     : foo datasource:jdbc:h2:mem:foo
2020-10-21 02:55:46.541  INFO 3688 --- [           main] c.g.m.MultiDatasourceDemoApplication     : bar datasource:jdbc:h2:mem:bar
```



### 那些好用的连接池们：HikariCp

Hikari是日语，它的原意是光

#### HikariCp为什么那么快

1. 字节码级别优化（很多方法通过JavaAssist生成）
2. 大量的小改进
   - 用FastStatementList代替了ArrayList
   - 无锁集合 ConcurrentBag
   - 代理类的优化（比如：用invokestatic代替了invokevirtual）

#### 常用 HikariCP 配置参数

常用配置：

- spring.datasource.hikari.maximumPoolSize=10
- spring.datasource.hikari.minimumIdle=10
- spring.datasource.hikari.idleTimeout=600000
- spring.datasource.hikari.connectionTimeout=30000
- spring.datasource.hikari.maxLifetime=1800000
- Hikari官网上有关于更多配置的详情信息...



#### 在Spring Boot中的配置

对于Spring Boot 1.x版本，默认使用的是Tomcat的连接池，并且Spring Boot 1.x版本现在已经很少了

对于Spring Boot 2.x版本来说，默认使用的就是HikariCP连接池，我们只需要在配置文件中对spring.datasource.hikari.*进行配置即可



### 那些好用的连接池们：Alibaba Druid

#### Druid

经过了阿里巴巴各大系统的考验，值得信赖

其实用的功能包括：

- 详细的监控
- ExceptionSorter，针对主流数据库的返回码都有支持
- SQL防注入
- 内置加密配置
- 众多扩展点，方便进行定制



#### Druid数据源的配置

1. 直接配置DruidDataSource

2. 通过添加依赖：druid-spring-boot-starter，然后在配置文件中对spring.datasource.bruid.*进行配置即可

   ```reStructuredText
   Filter配置
   spring.datasource.druid.filters=stat,config,wall,log4j
   
   密码加密
   spring.datasource.password=<加密密码>
   spring.datasource.druid.filter.config.enabled=true
   spring.datasource.druid.connection-properties=config.decrypt=true;config.decrypt.key=<public-key>
   
   SQL防注入
   spring.datasource.druid.filter.wall.enabled=true
   spring.datasource.druid.filter.wall.db-type=h2
   spring.datasource.druid.filter.wall.config.delete-allow=false
   spring.datasource.druid.filter.wall.config.drop-table-allow=false
   ```

#### Druid Filter

- 用于定制连接池操作的各种缓解
- 可以继承FilterEventAdapter 以方便地实现Filter
- 修改META-INF/druid-filter.properties 增加Filter的配置

#### 选择连接池的考量点

- 可靠性
- 性能
- 功能
- 可运维性
- 可扩展性
- 其他...



### 如何通过Spring JDBC访问数据库

### 什么是Spring的事务抽象

### 了解Spring的JDBC异常抽象





