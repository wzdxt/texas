package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dai_x on 17-9-6.<br/>
 * screen and anchor must have same size
 */
@Component
public class ImageComparator {
    @Autowired
    DisplayerConfigure configure;

    public int calcPointMistake(BufferedImage screen, BufferedImage anchor, int x, int y) {
        return RgbUtil.calcRgbMistake(anchor.getRGB(x, y), screen.getRGB(x, y));
    }

    public int calcLineMistake(BufferedImage screen, BufferedImage anchor, int x1, int y1, int x2, int y2) {
        List<Integer> mistake = new ArrayList<>();
        LineUtil.help(x1, y1, x2, y2, configure.getCheck().getLineStep(), (x, y) -> {
            try {
                int rgb = anchor.getRGB(x, y);
                int checkRgb = screen.getRGB(x, y);
                mistake.add(RgbUtil.calcRgbMistake(rgb, checkRgb));
            } catch (ArrayIndexOutOfBoundsException e) {
                throw e;
            }
        });
        return mistake.stream().reduce(0, (s, i) -> s + i) / mistake.size();
    }

    public int calcAreaMistake(BufferedImage screen, BufferedImage anchor, int x1, int y1, int x2, int y2) {
        List<Integer> mistake = new ArrayList<>();
        LineUtil.help(x1, y1, x2 - 1, y1, configure.getCheck().getLineStep(), (x, _1) -> {
            LineUtil.help(x, y1, x, y2 - 1, configure.getCheck().getLineStep(), (_2, y) -> {
                int rgb = anchor.getRGB(x, y);
                int checkRgb = screen.getRGB(x, y);
                mistake.add(RgbUtil.calcRgbMistake(rgb, checkRgb));
            });
        });
        return mistake.stream().reduce(0, (s, i) -> s + i) / mistake.size();
    }
}
