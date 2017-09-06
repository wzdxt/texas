package com.wzdxt.texas.business.display;

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
public class DisplayerImplTest {
    @Autowired
    DisplayerImpl displayer;
    @Autowired
    ScreenParam screenParam;

    @Test
    public void matchAnchor() throws Exception {
        displayer.matchAnchor();
        BufferedImage bi = new Robot().createScreenCapture(
                new Rectangle(screenParam.getGameX1(), screenParam.getGameY1(),
                        screenParam.getWidth(), screenParam.getHeight()));
        System.out.println(screenParam);
        displayer.save(bi);
    }

}