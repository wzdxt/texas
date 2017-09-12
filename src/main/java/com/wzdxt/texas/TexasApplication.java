package com.wzdxt.texas;

import com.wzdxt.texas.application.MainFrame;
import com.wzdxt.texas.application.UiAppender;
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
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;

@SpringBootApplication
@EnableConfigurationProperties
@AllArgsConstructor
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = {"com.wzdxt.texas"})
public class TexasApplication implements ApplicationRunner {
    private MasterConfigures configuration;
    private DisplayerConfigure  displayerConfigure;
    private Displayer displayer;
    private MainFrame mainFrame;

    public static void main(String[] args) {
        new SpringApplicationBuilder(TexasApplication.class).headless(false).run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        log.warn(String.valueOf(configuration.getFlop().getCheck()));
        EventQueue.invokeLater(() -> {
            mainFrame.setVisible(true);
            UiAppender.getInstance().setMainFrame(mainFrame);
        });
//        displayer.getCurrentStatus();
    }
}

