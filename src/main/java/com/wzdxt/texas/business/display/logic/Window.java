package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.util.RobotUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/10.
 */
@Component
public class Window {
    @Autowired
    ScreenParam screenParam;

    public BufferedImage capture(int x1, int y1, int x2, int y2) {
        return RobotUtil.screenCapture(
                screenParam.getGameX1() + x1,
                screenParam.getGameY1() + y1,
                screenParam.getGameX1() + x2,
                screenParam.getGameY1() + y2);
    }

}
