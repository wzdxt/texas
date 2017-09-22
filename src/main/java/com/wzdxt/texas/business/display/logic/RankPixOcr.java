package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dai_x on 17-9-21.
 */
@Component
public class RankPixOcr {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ImageComparator imageComparator;
    @Autowired
    private ImageCutter imageCutter;

    public static int N = 3;
    public static int M = 5;

    public String ocr(BufferedImage bi) {
        int background = imageComparator.getBackgroundRgb(bi);
        bi = imageCutter.cutEdge(bi);
        if (bi == null) return null;
        int width = bi.getWidth();
        int height = bi.getHeight();
        int total = width * height;
        int[] pix = new int[15];
        int lastX = 0;
        for (int i = 1; i <= N; i++) {
            int fromX = lastX;
            int toX = Math.round((width) / N * i);
            lastX = toX;

            int lastY = 0;
            for (int j = 1; j <= M; j++) {
                int fromY = lastY;
                int toY = Math.round((height) / M * j);
                lastY = toY;

                int p = (i - 1) * M + j - 1;
                for (int x = fromX; x < toX; x++) {
                    for (int y = fromY; y < toY; y++) {
                        try {
                            if (RgbUtil.calcRgbMistake(background, bi.getRGB(x, y)) > configure.getCheck().getRgbMistake()) {
                                pix[p]++;
                            }
                        } catch (Exception e) {
                            throw e;
                        }
                    }
                }
            }
        }

        double best = 1e10;
        String matched = null;
        for (Map.Entry<String, double[]> entry : configure.getOcrPix().entrySet()) {
            double sum = 0;
            for (int p = 0; p < N * M; p++) {
                double rate = pix[p] * 1.0 / total;
                sum += Math.abs(pix[p] * 1.0 / total - entry.getValue()[p]);
            }
            if (sum < best) {
                best = sum;
                matched = entry.getKey();
            }
        }
        return matched;
    }

}


