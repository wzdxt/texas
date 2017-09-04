package com.wzdxt.texas;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class TexasApplication implements CommandLineRunner {
    @Autowired
    private Configuration configuration;

    public static void main(String[] args) {
        SpringApplication.run(TexasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.warn(String.valueOf(configuration.flop.check));
    }
}
