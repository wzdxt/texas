package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
public abstract class AbsCheck implements Operation {
    DisplayerConfigure configure;

    AbsCheck(DisplayerConfigure configure) {
        this.configure = configure;
    }

    BufferedImage screenCapture(int x1, int y1, int x2, int y2) {
        try {
            return new Robot().createScreenCapture(new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1));
        } catch (AWTException ignored) {
        }
        return null;
    }

    BufferedImage screenCapture(int x, int y) {
        return screenCapture(x, y, x, y);
    }

    void delay(double sec) {
        try {
            Thread.sleep((long)(sec * 1000));
        } catch (InterruptedException ignored) {
        }
    }

    int calcRgbMistake(int rgb, int checkRgb) {
        return RgbUtil.calcRgbMistake(rgb, checkRgb);
    }

    int calcRgbMistake(int rgb1, int rgb2, int checkRgb) {
        return RgbUtil.calcRgbMistake(rgb1, rgb2, checkRgb);
    }

    @Override
    public boolean perform() {
        return perform(3);
    }

    public boolean perform(int retry) {
        for (int i = 0; i < retry; i++) {
            int mistake = check();
            if (mistake < configure.getCheck().getRgbMistake()) {
                return true;
            }
            if (i < retry - 1) delay(0.2);
        }
        return false;
    }

    abstract int check();

    public static List<AbsCheck> fromConfig(DisplayerConfigure configure, ScreenParam screenParam, DisplayerConfigure.CheckGroup checkGroup) {
        List<AbsCheck> list = new ArrayList<>();
        for (DisplayerConfigure.CheckOperation operation : checkGroup) {
            if (operation.point != null) {
                list.add(new CheckPoint(configure,
                        screenParam.getGameX1() + operation.point[0],
                        screenParam.getGameY1() + operation.point[1],
                        operation.point[2]));
            }
        }
        return list;
    }
}
