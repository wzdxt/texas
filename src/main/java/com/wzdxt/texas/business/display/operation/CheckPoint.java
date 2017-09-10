package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.ScreenParam;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
@NoArgsConstructor
public class CheckPoint extends AbsCheck {
    int x, y;
    int rgb;
    @Autowired
    ScreenParam screenParam;

    @Override
    public void set(int[] p) {
        this.set(p[0], p[1], p[2]);
    }

    public void set(int x, int y, int rgb) {
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
