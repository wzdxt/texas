package com.wzdxt.texas.business.display.operation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class CheckLineRange extends AbsCheck {
    int x1, y1, x2, y2;
    int rgb1, rgb2;

    @Override
    public AbsCheck set(int[] p) {
        this.set(p[0], p[1], p[2], p[3], p[4], p[5]);
        return this;
    }

    public void set(int x1, int y1, int x2, int y2, int rgb1, int rgb2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.rgb1 = rgb1;
        this.rgb2 = rgb2;
    }

    @Override
    public int check() {
        int cnt = 0;
        int mistake = 0;

        int dx = x2 - x1;
        int dy = y2 - y1;
        int length = (int)Math.round(Math.sqrt(dx * dx + dy * dy));
        int step = length / configure.getCheck().getLineStep();
        int px = x1, py = y1;
        BufferedImage bi = screenCapture();
        while ((x2 - px) * (px - x1) >= 0 && (y2 - py) * (py - y1) >= 0) {
            int cx = px - x1;
            if (cx < 0) cx += bi.getWidth();
            int cy = py - y1;
            if (cy < 0) cy += bi.getHeight();
            int checkRgb = bi.getRGB(cx, cy);
            mistake += calcRgbMistake(rgb1, rgb2, checkRgb);
            cnt++;
            px += dx / step;
            py += dy / step;
        }

        return mistake / cnt;
    }

    BufferedImage screenCapture() {
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2) + 1;
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2) + 1;
        return screenCapture(left, top, right, bottom);
    }
}
