package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.util.LineUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class CheckSame extends AbsCheck {
    int x1, y1, x2, y2;

    @Override
    public AbsCheck set(int[] p) {
        this.set(p[0], p[1], p[2], p[3]);
        return this;
    }

    /**
     * for line
     */
    public void set(int x1, int y1, int x2, int y2) {
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
        LineUtil.walk(x1, y1, x2, y2, configure.getCheck().getLineStep(), (x, y) -> allRgb.add(bi.getRGB(x, y)));

        int aveR = mistake.stream().map(m -> (m & 0xff0000) >> 16).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> (m & 0xff0000) >> 16).map(r -> Math.abs(r - aveR))
                .reduce(0, (s, i) -> s + i) / mistake.size();
        int aveG = mistake.stream().map(m -> (m & 0x00ff00) >> 8).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> (m & 0x00ff00) >> 8).map(g -> Math.abs(g - aveG))
                .reduce(0, (s, i) -> s + i) / mistake.size();
        int aveB = mistake.stream().map(m -> m & 0x0000ff).reduce(0, (s, i) -> s + i) / mistake.size();
        ret += mistake.stream().map(m -> m & 0x0000ff).map(b -> Math.abs(b - aveB))
                .reduce(0, (s, i) -> s + i) / mistake.size();

        return ret;
    }


    BufferedImage screenCapture() {
        int left = Math.min(x1, x2);
        int right = Math.max(x1, x2) + 1;
        int top = Math.min(y1, y2);
        int bottom = Math.max(y1, y2) + 1;
        return screenCapture(left, top, right, bottom);
    }
}
