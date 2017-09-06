package com.wzdxt.texas.business.display.util;

/**
 * Created by dai_x on 17-9-6.
 */
public class RgbUtil {

    /**
     * check exact rgb
     * @param rgb
     * @param checkRgb
     * @return
     */
    public static int calcRgbMistake(int rgb, int checkRgb) {
        int r = rgb & 0xff0000 >> 16;
        int g = rgb & 0x00ff00 >> 8;
        int b = rgb & 0x0000ff;
        int rr = checkRgb & 0xff0000 >> 16;
        int gg = checkRgb & 0x00ff00 >> 8;
        int bb = checkRgb & 0x0000ff;
        return Math.abs(r - rr) + Math.abs(g - gg) + Math.abs(b - bb);
    }

    /**
     * check rgb range
     * @param rgb1
     * @param rgb2
     * @param checkRgb
     * @return
     */
    public static int calcRgbMistake(int rgb1, int rgb2, int checkRgb) {
        int r1 = rgb1 & 0xff0000 >> 16;
        int g1 = rgb1 & 0x00ff00 >> 8;
        int b1 = rgb1 & 0x0000ff;
        int r2 = rgb2 & 0xff0000 >> 16;
        int g2 = rgb2 & 0x00ff00 >> 8;
        int b2 = rgb2 & 0x0000ff;
        int rr = checkRgb & 0xff0000 >> 16;
        int gg = checkRgb & 0x00ff00 >> 8;
        int bb = checkRgb & 0x0000ff;
        int mistake = 0;
        if (rr > r1 && rr > r2) mistake += Math.min(Math.abs(rr-r1), Math.abs(rr-r2));
        if (gg > g1 && gg > g2) mistake += Math.min(Math.abs(gg-g1), Math.abs(gg-g2));
        if (bb > b1 && bb > b2) mistake += Math.min(Math.abs(bb-b1), Math.abs(bb-b2));
        return mistake;
    }

}
