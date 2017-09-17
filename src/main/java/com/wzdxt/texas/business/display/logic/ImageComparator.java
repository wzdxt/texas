package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.LineUtil;
import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.util.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by dai_x on 17-9-6.<br/>
 * window and anchor must have same size
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
        LineUtil.walk(x1, y1, x2 - 1, y2 - 1, configure.getCheck().getLineStep(), (x, y) -> {
            try {
                int rgb = anchor.getRGB(x, y);
                int checkRgb = screen.getRGB(x, y);
                return mistake.add(RgbUtil.calcRgbMistake(rgb, checkRgb));
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
        LineUtil.walk(x1, y1, x2 - 1, y1, step, (x, _1) ->
                LineUtil.walk(x, y1, x, y2 - 1, step, (_2, y) -> {
                    int rgb = anchor.getRGB(x, y);
                    int checkRgb = screen.getRGB(x, y);
                    return mistake.add(RgbUtil.calcRgbMistake(rgb, checkRgb));
                })
        );
        return mistake.stream().reduce(0, (s, i) -> s + i) / mistake.size();
    }

    /**
     * candidate with different size from <i>screen</i> will be ignored <br/>
     * only for small image
     *
     * @param screen
     * @param candidateMap
     * @param <T>
     */
    public <T> Tuple<T, Integer> findSimilar(BufferedImage screen, Map<T, BufferedImage> candidateMap) {
        int mistake = (int)1e10;
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
        int backgroundRgb = getBackgroundRgb(image);
        int front = 0;
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (RgbUtil.calcRgbMistake(image.getRGB(i, j), backgroundRgb) < configure.getCheck().getRgbMistake()) {
                    front++;
                }
            }
        }
        return front * 1.0 / width * height;
    }

    public int getBackgroundRgb(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        List<Integer> allRgb = new ArrayList<>((width + height) * 2);
        for (int i = 0; i < width; i++) {
            allRgb.add(image.getRGB(i, 0));
            allRgb.add(image.getRGB(i, height - 1));
        }
        for (int j = 0; j < height; j++) {
            allRgb.add(image.getRGB(0, j));
            allRgb.add(image.getRGB(width - 1, j));
        }
        List<Map.Entry<Integer, Long>> entryList =
                allRgb.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream().sorted(Map.Entry.<Integer, Long>comparingByValue().reversed()).collect(Collectors.toList());
        return entryList.get(0).getKey();
    }

    /**
     * only for tiny image
     *
     * @param image
     * @param background
     * @return
     */
    public int getFrontRgb(BufferedImage image, int background) {
        int width = image.getWidth();
        int height = image.getHeight();
        List<Integer> allRgb = new ArrayList<>(width * height);
        for (int i = 0; i < width; i += 2) {
            for (int j = 0; j < height; j += 2) {
                int rgb = image.getRGB(i, j);
                if (RgbUtil.calcRgbMistake(rgb, background) < configure.getCheck().getRgbMistake())
                    allRgb.add(rgb);
            }
        }
        List<Map.Entry<Integer, Long>> entryList =
                allRgb.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream().sorted(Map.Entry.<Integer, Long>comparingByValue().reversed()).collect(Collectors.toList());
        return entryList.get(0).getKey();
    }

}

