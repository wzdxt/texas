package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckPoint extends AbsCheck {
    int x, y;
    int rgb;

    public CheckPoint(DisplayerConfigure configure, int x, int y, int rgb) {
        super(configure);
        this.x = x;
        this.y = y;
        this.rgb = rgb;
    }

    @Override
    public int check() {
        BufferedImage bi = screenCapture(x, y);
        int checkRgb = bi.getRGB(0, 0);
        return calcRgbMistake(rgb, checkRgb);
    }
}
