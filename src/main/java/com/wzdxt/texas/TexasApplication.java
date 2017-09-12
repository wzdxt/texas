package com.wzdxt.texas;

import com.wzdxt.texas.application.CalculatorMainFrame;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.config.MasterConfigures;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.awt.*;

@Slf4j
@SpringBootApplication
@EnableConfigurationProperties
@AllArgsConstructor
@EnableCaching
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class TexasApplication implements ApplicationRunner {
    private MasterConfigures configuration;
    private DisplayerConfigure  displayerConfigure;
    private Displayer displayer;
    private ApplicationContext ctx;

    public static void main(String[] args) {
        new SpringApplicationBuilder(TexasApplication.class).headless(false).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.warn(String.valueOf(configuration.getFlop().getCheck()));
        EventQueue.invokeLater(() -> {
            CalculatorMainFrame ex = ctx.getBean(CalculatorMainFrame.class);
            ex.setVisible(true);
        });
//        displayer.getCurrentStatus();
    }
}

