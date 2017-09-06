package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.util.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        return calcAreaMistake(screen, anchor, x1, y1, x2, y2, configure.getCheck().getLineStep());
    }

    public int calcAreaMistake(BufferedImage screen, BufferedImage anchor, int x1, int y1, int x2, int y2, int step) {
        List<Integer> mistake = new ArrayList<>();
        LineUtil.help(x1, y1, x2 - 1, y1, step, (x, _1) -> {
            LineUtil.help(x, y1, x, y2 - 1, step, (_2, y) -> {
                int rgb = anchor.getRGB(x, y);
                int checkRgb = screen.getRGB(x, y);
                mistake.add(RgbUtil.calcRgbMistake(rgb, checkRgb));
            });
        });
        return mistake.stream().reduce(0, (s, i) -> s + i) / mistake.size();
    }

    /**
     * candidate with different size from <i>screen</i> will be ignored <br/>
     * only for small image
     * @param screen
     * @param candidateMap
     * @param <T>
     */
    public <T> Tuple<T, Integer> findSimilar(BufferedImage screen, Map<T, BufferedImage> candidateMap) {
        int mistake = (int) 1e10;
        T choose = null;
        for (Map.Entry<T, BufferedImage> entry : candidateMap.entrySet()) {
            BufferedImage candidate = entry.getValue();
            if (screen.getHeight() != candidate.getHeight() || screen.getWidth() != candidate.getWidth()) {
                int m = calcAreaMistake(screen, candidate, 0, 0, screen.getWidth(), screen.getHeight(), 1);
                if (m < mistake) {
                    choose = entry.getKey();
                    mistake = m;
                }
            }
        }
        return Tuple.of(choose, mistake);
    }

    public double getPixRate(BufferedImage image) {
        
    }
}

