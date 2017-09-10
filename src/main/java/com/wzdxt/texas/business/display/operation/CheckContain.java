package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.business.display.util.RgbUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class CheckContain extends AbsCheck {
    int x1, y1, x2, y2;
    int rgb;

    @Override
    public AbsCheck set(int[] p) {
        this.set(p[0], p[1], p[2], p[3], p[4]);
        return this;
    }

    public void set(int x1, int y1, int x2, int y2, int rgb) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.rgb = rgb;
    }

    @Override
    public int check() {
        BufferedImage bi = screenCapture(x1, y1, x2, y2);
        Result res = new Result((int)1e9);

        LineUtil.walk(x1, y1, x2 - 1, y1, 1, (x, _1) ->
                LineUtil.walk(x, y1, x, y2 - 1, 1, (_2, y) -> {
                    int checkRgb = bi.getRGB(x, y);
                    int m = RgbUtil.calcRgbMistake(rgb,checkRgb);
                    res.mistake = Math.min(res.mistake, m);
                    return true;
                })
        );
        return res.mistake;
    }

    @AllArgsConstructor
    private class Result {
        public int mistake;
    }
}
