## 第一个Spring程序
#### Spring Initializr
Spring initializr是一个可以自动生成Spring项目骨架的工具：`https://start.spring.io/`

在添加的依赖里面选择 Actuator 以及 Spring Web

Actuator的作用是在生产环境中监控当前应用的健康，虚拟机等信息，并且通过前段以可视化的界面显示出来

举例：

在启动应用后，访问：`http://localhost:8080/actuator/health`,则会看到信息：

```json
{"status":"UP"}
```

说明应用的启动状态正常

Spring Web则是SpringWeb应用程序最基本的依赖

#### 第一个可以启动的Spring程序
```java
package com.geektime.hellospring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloSpringApplication.class, args);
	}

	@RequestMapping("/hello")
	public String hello(){
		return "Hello Spring";
	}
}
```
上面的程序中：

1. @SpringBootApplication 注解标注了SpringBoot程序的入口
2. @RestController 注解标注了该模块是一个Controller模块

启动应用，运行后访问`http://localhost:8080/hello`

就可以看到页面返回的"Hello Spring" 

#### 运行Spring程序
1. 第一种方法直接通过IDE的运行按钮运行
2. 第二种方式可以使用mvn将应用打包后使用java运行jar包的方式来启动程序
    
    使用命令：
    ```
    mvn clean package -Dmaven.test.skip
    ```
    然后使用命令：
    ```
    java -jar /target/***.jar 
    ```
   