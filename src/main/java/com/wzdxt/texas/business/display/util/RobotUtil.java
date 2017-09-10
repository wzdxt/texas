package com.wzdxt.texas.business.display.util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by wzdxt on 2017/9/10.
 */
public class RobotUtil {

    public static BufferedImage screenCapture(int x1, int y1, int x2, int y2) {
        try {
            return new Robot().createScreenCapture(new Rectangle(x1, y1, x2 - x1 + 1, y2 - y1 + 1));
        } catch (AWTException ignored) {
        }
        return null;
    }

    public static BufferedImage screenCapture(int x, int y) {
        return screenCapture(x, y, x + 1, y + 1);
    }

}
