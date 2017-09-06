package com.wzdxt.texas;

import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.config.MasterConfigures;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@AllArgsConstructor
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class TexasApplication implements ApplicationRunner {
    private MasterConfigures configuration;
    private DisplayerConfigure  displayerConfigure;
    private Displayer displayer;

    public static void main(String[] args) {
        SpringApplication.run(TexasApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.warn(String.valueOf(configuration.getFlop().getCheck()));
        displayer.getCurrentStatus();
    }
}
