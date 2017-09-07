package com.wzdxt.texas.business.display.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Created by dai_x on 17-9-6.
 */
@Component
public class LineUtil {
    /**
     * x2, y2 为线条末端的坐标，无需+1
     */
    public static boolean walk(int x1, int y1, int x2, int y2, int step, BiFunction<Integer, Integer, Object> function) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        int length = (int)Math.round(Math.sqrt(dx * dx + dy * dy));
        int stepNum = length / step;
        double px = x1, py = y1;
        while ((x2 - px) * (px - x1) >= 0 && (y2 - py) * (py - y1) >= 0) {
            Object r = function.apply((int)px, (int)py);
            if (r == null || !Boolean.valueOf(r.toString())) return false;
            if (stepNum == 0) break;
            px += dx / stepNum;
            py += dy / stepNum;
        }
        return true;

    }


}
