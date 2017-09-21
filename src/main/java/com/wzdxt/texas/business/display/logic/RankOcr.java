package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dai_x on 17-9-21.
 */
@Component
public class RankOcr {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ImageComparator imageComparator;
    @Autowired
    private ImageCutter imageCutter;

    public String ocr(BufferedImage bi) {
        int background = imageComparator.getBackgroundRgb(bi);
        bi = imageCutter.cutEdge(bi);
        int width = bi.getWidth();
        int height = bi.getHeight();
        int total = width * height;
        int[] pix = new int[15];
        int lastX = -1;
        for (int i = 1; i <= 3; i++) {
            int fromX = lastX + 1;
            int toX = (int) Math.floor(width / 3.0 * i + 1e-5);
            lastX = toX;

            int lastY = -1;
            for (int j = 1; j <= 5; j++) {
                int fromY = lastY + 1;
                int toY = (int) Math.floor(height / 5.0 * j + 1e-5);
                lastY = toY;

                int p = (i - 1) * 5 + j - 1;
                for (int x = fromX; x <= toX; x++) {
                    for (int y = fromY; x <= toY; y++) {
                        if (RgbUtil.calcRgbMistake(background, bi.getRGB(x, y)) > configure.getCheck().getRgbMistake()) {
                            pix[p]++;
                        }
                    }
                }
            }
        }

        double best = 1e10;
        String matched = null;
        for (Map.Entry<String, ArrayList<Double>> entry : new rankDist().entrySet()) {
            double sum = 0;
            for (int p = 0; p < 14; p++) {
                sum += Math.abs(pix[p] * 1.0 / total - entry.getValue().get(p));
            }
            if (sum < best) {
                best=  sum;
                matched = entry.getKey();
            }
        }
        return matched;
    }

    private static class rankDist extends HashMap<String, ArrayList<Double>> {
    }
}
