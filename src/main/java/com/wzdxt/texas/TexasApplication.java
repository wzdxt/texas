package com.wzdxt.texas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class TexasApplication {

	public static void main(String[] args) {
		SpringApplication.run(TexasApplication.class, args);
	}
}
