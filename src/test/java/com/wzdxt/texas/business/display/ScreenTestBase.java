package com.wzdxt.texas.business.display;

import com.wzdxt.texas.TexasApplication;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.util.RobotUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Created by wzdxt on 2017/9/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TexasApplication.class)
public class ScreenTestBase {

    protected void setScreen(URI uri) {
        try {
            Config.bi = ImageIO.read(new File(uri));
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
            };
        }
    }
}
