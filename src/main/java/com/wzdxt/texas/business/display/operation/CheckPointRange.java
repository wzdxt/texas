package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class CheckPointRange extends AbsCheck {
    int x, y;
    int rgb1, rgb2;

    @Override
    public void set(int[] p) {
        this.set(p[0], p[1], p[2], p[3]);
    }

    public void set(int x, int y, int rgb1, int rgb2) {
        this.x = x;
        this.y = y;
        this.rgb1 = rgb1;
        this.rgb2 = rgb2;
    }

    @Override
    public int check() {
        BufferedImage bi = screenCapture(x, y);
        int checkRgb = bi.getRGB(0, 0);
        return calcRgbMistake(rgb1, rgb2, checkRgb);
    }
}
