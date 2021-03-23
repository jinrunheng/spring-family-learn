package com.geektime.mybatisgeneratordemo;

import com.geektime.mybatisgeneratordemo.mapper.CoffeeMapper;
import com.geektime.mybatisgeneratordemo.model.Coffee;
import com.geektime.mybatisgeneratordemo.model.CoffeeExample;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("com.geektime.mybatisgeneratordemo.mapper")
public class MybatisGeneratorDemoApplication implements ApplicationRunner {

	@Autowired
	private CoffeeMapper coffeeMapper;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// generateArtifacts();
		playWithArtifacts();
	}

	public static void main(String[] args) {
		SpringApplication.run(MybatisGeneratorDemoApplication.class, args);
	}

	public void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml")
		);
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,callback,warnings);
		myBatisGenerator.generate(null);
	}

	private void playWithArtifacts(){
		Coffee espresso = new Coffee()
				.withName("espresso")
				.withPrice(Money.of(CurrencyUnit.of("CNY"),20.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());

		Coffee latte = new Coffee()
				.withName("latte")
				.withPrice(Money.of(CurrencyUnit.of("CNY"),30.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());

		coffeeMapper.insert(espresso);
		coffeeMapper.insert(latte);

		Coffee c1 = coffeeMapper.selectByPrimaryKey(1L);
		Coffee c2 = coffeeMapper.selectByPrimaryKey(2L);
		log.info("Coffee {}",c1);
		log.info("Coffee {}",c2);

		// Example
		CoffeeExample example = new CoffeeExample();
		example.createCriteria().andNameEqualTo("latte");
		List<Coffee> coffees = coffeeMapper.selectByExample(example);
		for (Coffee coffee : coffees) {
			log.info("Coffee:{}",coffee);
		}
	}

}
