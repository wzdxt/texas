package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckPoint extends AbsCheck {
    Point point;
    int rgb;
    int r, g, b;

    public CheckPoint(DisplayerConfigure configure, Point p, int rgb) {
        super(configure);
        this.point = p;
        this.rgb = rgb;
        this.r = rgb & 0xff0000 >> 16;
        this.g = rgb & 0x00ff00 >> 8;
        this.b = rgb & 0x0000ff;
    }

    @Override
    public int check() {
        BufferedImage bi = screenCapture(point.x, point.y);
        int checkRgb = bi.getRGB(0, 0);
        return calcRgbMistake(rgb, checkRgb);
    }
}
