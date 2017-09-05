package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckPointRange extends AbsCheck {
    Point point;
    int rgb1, rgb2;

    public CheckPointRange(DisplayerConfigure configure, Point p, int rgb1, int rgb2) {
        super(configure);
        this.point = p;
        this.rgb1 = rgb1;
        this.rgb2 = rgb2;
    }

    @Override
    public int check() {
        BufferedImage bi = screenCapture(point.x, point.y);
        int checkRgb = bi.getRGB(0, 0);
        return calcRgbMistake(rgb1, rgb2, checkRgb);
    }
}
