package com.geektime.springbootautoconfigure;

import com.geektime.greeting.GreetingApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(GreetingApplicationRunner.class)
public class GreetingAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(GreetingApplicationRunner.class)
    @ConditionalOnProperty(name = "greeting.enabled", havingValue = "true", matchIfMissing = true)
    // name 为 配置的名字
    // havingValue 是与配置的值对比值，当两个值相同返回true,配置类生效.
    public GreetingApplicationRunner greetingApplicationRunner() {
        return new GreetingApplicationRunner();
    }
}
