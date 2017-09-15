package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
@NoArgsConstructor
public abstract class AbsCheck implements Operation {
    @Autowired
    protected DisplayerConfigure configure;
    @Autowired
    protected GameWindow window;

    BufferedImage screenCapture(int x1, int y1, int x2, int y2) {
        return window.capture(x1, y1, x2, y2);
    }

    BufferedImage screenCapture(int x, int y) {
        return screenCapture(x, y, x + 1, y + 1);
    }

    void delay(double sec) {
        try {
            Thread.sleep((long)(sec * 1000));
        } catch (InterruptedException ignored) {
        }
    }

    int calcRgbMistake(int rgb, int checkRgb) {
        return RgbUtil.calcRgbMistake(rgb, checkRgb);
    }

    int calcRgbMistake(int rgb1, int rgb2, int checkRgb) {
        return RgbUtil.calcRgbMistake(rgb1, rgb2, checkRgb);
    }

    @Override
    public boolean perform() {
        return perform(3);
    }

    public boolean perform(int retry) {
        for (int i = 0; i < retry; i++) {
            int mistake = check();
            if (mistake < configure.getCheck().getRgbMistake()) {
                return true;
            }
            if (i < retry - 1) {
                delay(0.2);
                window.refresh();
            }
        }
        return false;
    }

    public abstract int check();

    @Override
    abstract public AbsCheck set(int[] p);

    public static List<AbsCheck> fromConfig(ApplicationContext cxt, DisplayerConfigure.CheckOperationList checkGroup) {
        List<AbsCheck> list = new ArrayList<>();
        for (DisplayerConfigure.CheckOperation operation : checkGroup) {
            AbsCheck check = null;
            if (operation.point != null && operation.point.length > 0) {
                check = cxt.getBean(CheckPoint.class);
                check.set(operation.point);
            } else if (operation.contain != null && operation.contain.length > 0) {
                check = cxt.getBean(CheckContain.class);
                check.set(operation.contain);
            } else if (operation.line != null && operation.line.length > 0) {
                check = cxt.getBean(CheckLine.class);
                check.set(operation.line);
            } else if (operation.lineRange != null && operation.lineRange.length > 0) {
                check = cxt.getBean(CheckLineRange.class);
                check.set(operation.lineRange);
            } else if (operation.pointRange != null && operation.pointRange.length > 0) {
                check = cxt.getBean(CheckPointRange.class);
                check.set(operation.pointRange);
            } else if (operation.same != null && operation.same.length > 0) {
                check = cxt.getBean(CheckSame.class);
                check.set(operation.pointRange);
            }
            if (check != null) {
                list.add(check);
            }
        }
        return list;
    }
}
