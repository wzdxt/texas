package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckSame extends AbsCheck {
    int x1, y1, x2, y2;


    public CheckSame(DisplayerConfigure configure, int x1, int y1, int x2, int y2) {
        super(configure);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
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
        List<Integer> allRgb = new ArrayList<>();
        while ((x2 - px) * (px - x1) >= 0 && (y2 - py) * (py - y1) >= 0) {
            int cx = px - x1;
            if (cx < 0) cx += bi.getWidth();
            int cy = py - y1;
            if (cy < 0) cy += bi.getHeight();
            int checkRgb = bi.getRGB(cx, cy);
            allRgb.add(checkRgb);
            px += dx / step;
            py += dy / step;
        }

        // calc avg
        // calc mistake

        return mistake / cnt;
    }



    BufferedImage screenCapture() {
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);
        return screenCapture(left, top, right, bottom);
    }
}
