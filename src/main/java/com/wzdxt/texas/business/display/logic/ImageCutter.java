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

/**
 * Created by wzdxt on 2017/9/6.
 */
@Component
public class ImageCutter {
    @Autowired
    DisplayerConfigure configure;
    @Autowired
    ImageComparator imageComparator;

    public BufferedImage cutEdge(BufferedImage image) {
        int background = imageComparator.getBackgroundAndFrontRgb(image).left;
        int width = image.getWidth();
        int height = image.getHeight();
        int top, bottom, left, right;
        topfor:
        for (top = 0; top < height; top++) {
            for (int i = 0; i < width; i++) {
                if (RgbUtil.calcRgbMistake(image.getRGB(i, top), background) > configure.getCheck().getRgbMistake())
                    break topfor;
            }
        }
        bottomfor:
        for (bottom = height - 1; bottom >= 0; bottom--) {
            for (int i = 0; i < width; i++) {
                if (RgbUtil.calcRgbMistake(image.getRGB(i, bottom), background) > configure.getCheck().getRgbMistake())
                    break bottomfor;
            }
        }
        leftfor:
        for (left = 0; left < width; left++) {
            for (int j = 0; j < height; j++) {
                if (RgbUtil.calcRgbMistake(image.getRGB(left, j), background) > configure.getCheck().getRgbMistake())
                    break leftfor;
            }
        }
        rightfor:
        for (right = width - 1; right >= 0; right--) {
            for (int j = 0; j < height; j++) {
                if (RgbUtil.calcRgbMistake(image.getRGB(right, j), background) > configure.getCheck().getRgbMistake())
                    break rightfor;
            }
        }
        return image.getSubimage(left, top, right - left + 1, bottom - top + 1);
    }

    public List<BufferedImage> cutCharactors(BufferedImage image) {
        List<BufferedImage> ret = new ArrayList<>();
        Tuple<Integer, Integer> tuple = imageComparator.getBackgroundAndFrontRgb(image);
        int background = tuple.left;
        int front = tuple.right;
        int width = image.getWidth();
        int height = image.getHeight();
        int left = 0;
        boolean inChar = false;
        for (int i = 0; i < width; i++) {
            boolean foundPix = false;
            foundPix = !LineUtil.walk(i, 0, i, height - 1, 1,
                    (x, y) -> {
                        int m1 = (RgbUtil.calcRgbMistake(image.getRGB(x, y), background));
                        int m2 = (RgbUtil.calcRgbMistake(image.getRGB(x, y), front));
                        return m1 < m2; // when find font pix, return false
                    }
            );
            if (inChar) {
                if (!foundPix) {
                    ret.add(image.getSubimage(left, 0, i - left, height));
                    inChar = false;
                }
            } else {
                if (foundPix) {
                    left = i;
                    inChar = true;
                }
            }
        }
        if (inChar) {
            ret.add(image.getSubimage(left, 0, width - left, height));
        }

        return ret;
    }

}

