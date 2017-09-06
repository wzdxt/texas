package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckLine extends AbsCheck {
    int x1, y1, x2, y2;
    int rgb;


    public CheckLine(DisplayerConfigure configure, int x1, int y1, int x2, int y2, int rgb) {
        super(configure);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.rgb = rgb;
    }

    @Override
    public int check() {
        List<Integer> mistake = new ArrayList<>();

        BufferedImage bi = screenCapture();

        LineUtil.help(x1, y1, x2, y2, configure.getCheck().getLineStep(), (px, py) -> {
            int cx = px - x1;
            if (cx < 0) cx += bi.getWidth();
            int cy = py - y1;
            if (cy < 0) cy += bi.getHeight();
            int checkRgb = bi.getRGB(cx, cy);
            mistake.add(calcRgbMistake(rgb, checkRgb));
        });

        return mistake.stream().reduce(0, (s, i) -> s+i) / mistake.size();
    }

    BufferedImage screenCapture() {
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);
        return screenCapture(left, top, right, bottom);
    }
}
