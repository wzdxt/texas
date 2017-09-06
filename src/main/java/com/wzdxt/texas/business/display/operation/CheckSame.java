package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class CheckSame extends AbsCheck {
    int x1, y1, x2, y2;


    /**
     * for line
     */
    public CheckSame(DisplayerConfigure configure, int x1, int y1, int x2, int y2) {
        super(configure);
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public int check() {
        int ret = 0;
        List<Integer> mistake = new ArrayList<>();

        BufferedImage bi = screenCapture();
        List<Integer> allRgb = new ArrayList<>();
        LineUtil.help(x1, y1, x2, y2, configure.getCheck().getLineStep(), (x, y) -> {
            int checkRgb = bi.getRGB(x, y);
            allRgb.add(checkRgb);
        });

        int aveR = mistake.stream().map(m -> m & 0xff0000 >> 16).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> m & 0xff0000 >> 16).map(r -> Math.abs(r - aveR))
                .reduce(0, (s, i) -> s + i) / mistake.size();
        int aveG = mistake.stream().map(m -> m & 0x00ff00 >> 8).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> m & 0x00ff00 >> 8).map(g -> Math.abs(g - aveG))
                .reduce(0, (s, i) -> s + i) / mistake.size();
        int aveB = mistake.stream().map(m -> m & 0x0000ff).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> m & 0x0000ff).map(b -> Math.abs(b - aveB))
                .reduce(0, (s, i) -> s + i) / mistake.size();

        return ret;
    }


    BufferedImage screenCapture() {
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2);
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2);
        return screenCapture(left, top, right, bottom);
    }
}
