package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.awt.image.BufferedImage;

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
        for (int i = 0; i < 5; i++) {
            int mistake = check();
            if (mistake < configure.getCheck().getRgbMistake()) {
                return true;
            }
            delay(1);
        }
        return false;
    }

    abstract int check();
}
