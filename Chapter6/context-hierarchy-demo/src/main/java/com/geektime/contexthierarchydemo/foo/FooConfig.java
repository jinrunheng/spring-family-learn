package com.geektime.contexthierarchydemo.foo;

import com.geektime.contexthierarchydemo.context.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class FooConfig {

    @Bean
    public TestBean testBeanX() {
        return new TestBean("foo");
    }

    @Bean
    public TestBean testBeanY() {
        return new TestBean("foo");
    }

//    @Bean
//    public FooAspect fooAspect() {
//        return new FooAspect();
//    }
}
