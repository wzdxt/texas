package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.Displayer;
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
        int r = rgb & 0xff0000 >> 16;
        int g = rgb & 0x00ff00 >> 8;
        int b = rgb & 0x0000ff;
        int rr = checkRgb & 0xff0000 >> 16;
        int gg = checkRgb & 0x00ff00 >> 8;
        int bb = checkRgb & 0x0000ff;
        return Math.abs(r - rr) + Math.abs(g - gg) + Math.abs(b - bb);
    }

    int calcRgbMistake(int rgb1, int rgb2, int checkRgb) {
        int r1 = rgb1 & 0xff0000 >> 16;
        int g1 = rgb1 & 0x00ff00 >> 8;
        int b1 = rgb1 & 0x0000ff;
        int r2 = rgb2 & 0xff0000 >> 16;
        int g2 = rgb2 & 0x00ff00 >> 8;
        int b2 = rgb2 & 0x0000ff;
        int rr = checkRgb & 0xff0000 >> 16;
        int gg = checkRgb & 0x00ff00 >> 8;
        int bb = checkRgb & 0x0000ff;
        int mistake = 0;
        if (rr > r1 && rr > r2) mistake += Math.min(Math.abs(rr-r1), Math.abs(rr-r2));
        if (gg > g1 && gg > g2) mistake += Math.min(Math.abs(gg-g1), Math.abs(gg-g2));
        if (bb > b1 && bb > b2) mistake += Math.min(Math.abs(bb-b1), Math.abs(bb-b2));
        return mistake;
    }

    @Override
    public void perform() {
        while (true) {
            int mistake = check();
            if (mistake < configure.getCheck().getRgbMistake()) {
                break;
            }
            delay(1);
        }
    }

    abstract int check();
}
