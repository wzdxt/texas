package com.wzdxt.texas.business.display;

import com.wzdxt.texas.TestUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisplayerTest extends ScreenTestBase {
    @Autowired
    Displayer displayer;
    @Autowired
    ScreenParam screenParam;

//    @Test
    public void matchAnchor() throws Exception {
        displayer.matchAnchor();
        BufferedImage bi = new Robot().createScreenCapture(
                new Rectangle(screenParam.getGameX1(), screenParam.getGameY1(),
                        screenParam.getWidth(), screenParam.getHeight()));
        System.out.println(screenParam);
        TestUtil.save(bi);
    }

    @Test
    public void test2PNG() throws Exception {
        switchTo("2.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.WAITING, status.getPhase());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/screen/" + s).toURI());
    }

}