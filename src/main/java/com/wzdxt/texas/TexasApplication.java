package com.wzdxt.texas;

import com.wzdxt.texas.application.MainFrame;
import com.wzdxt.texas.application.UiAppender;
import com.wzdxt.texas.business.display.Displayer;
import com.wzdxt.texas.config.Config;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.config.MasterConfigure;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;

@SpringBootApplication
@Import(Config.class)
@AllArgsConstructor
@Profile("main")
public class TexasApplication implements ApplicationRunner {
    private MasterConfigure configuration;
    private DisplayerConfigure  displayerConfigure;
    private Displayer displayer;
    private MainFrame mainFrame;

    public static void main(String[] args) {
        new SpringApplicationBuilder(TexasApplication.class).headless(false).profiles("main").run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        EventQueue.invokeLater(() -> {
            mainFrame.setVisible(true);
            UiAppender.getInstance().setMainFrame(mainFrame);
            displayer.run();
        });
    }
}

