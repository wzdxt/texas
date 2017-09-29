package com.wzdxt.texas.business.display;

import com.wzdxt.texas.TestBase;
import com.wzdxt.texas.TexasApplication;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.config.Config;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by wzdxt on 2017/9/15.
 */
abstract public class ScreenTestBase extends TestBase {

    protected void setScreen(InputStream is) {
        try {
            Config.bi = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Configuration
    public static class Config {
        public static BufferedImage bi;
        @Bean
        @Primary
        public GameWindow getGameWindow() {
            return new GameWindow(){
                @Override
                public BufferedImage capture(int x1, int y1, int x2, int y2) {
                    return bi.getSubimage(x1, y1, x2 - x1, y2 - y1);
                }
                @Override
                public BufferedImage refresh() {
                    return bi;
                }
            };
        }
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getResourceAsStream("/static/screen/" + s));
    }

}
