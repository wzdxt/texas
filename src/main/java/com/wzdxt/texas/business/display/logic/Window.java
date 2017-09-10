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
    private BufferedImage bi;

    public BufferedImage capture(int x1, int y1, int x2, int y2) {
        if (bi == null) refresh();
        return bi.getSubimage(x1, y1, x2 - x1, y2 - y1);
    }

    public BufferedImage refresh() {
        this.bi = RobotUtil.screenCapture(
                screenParam.getGameX1(), screenParam.getGameY1(),
                screenParam.getWidth(), screenParam.getHeight());
        return bi;
    }

}
