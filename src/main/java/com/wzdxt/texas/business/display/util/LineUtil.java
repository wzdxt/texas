package com.wzdxt.texas.business.display.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by dai_x on 17-9-6.
 */
@Component
public class LineUtil {
    public static void help(int x1, int y1, int x2, int y2, int step, BiConsumer<Integer, Integer> consumer) {
        double dx = x2 - x1 - 1;
        double dy = y2 - y1 - 1;
        int length = (int)Math.round(Math.sqrt(dx * dx + dy * dy));
        int stepNum = length / step;
        double px = x1, py = y1;
        while ((x2 - 1 - px) * (px - x1) >= 0 && (y2 - 1 - py) * (py - y1) >= 0) {
            consumer.accept((int)px, (int)py);
            if (stepNum == 0) break;
            px += dx / stepNum;
            py += dy / stepNum;
        }

    }


}
