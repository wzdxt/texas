package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.util.RgbUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
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
        int background = imageComparator.getBackgroundRgb(image);
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
        int background = imageComparator.getBackgroundRgb(image);
        int width = image.getWidth();
        int height = image.getHeight();
        int left = 0;
        boolean inChar = false;
        for (int i = 0; i < width; i++) {
            boolean foundPix = false;
            for (int j = 0; j < height; j++) {
                foundPix |= RgbUtil.calcRgbMistake(image.getRGB(i, j), background) > configure.getCheck().getRgbMistake();
                if (foundPix) break;
            }
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

        for (int i = 0; i < ret.size(); i++) {
            ret.set(i, cutEdge(ret.get(i)));
        }

        return ret;
    }

}

