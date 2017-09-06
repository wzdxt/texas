package com.wzdxt.texas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by wzdxt on 2017/9/6.
 */
public class TestUtil {
    public static void save(BufferedImage bi, String name) {
        File f = new File(name+".bmp");
        try {
            ImageIO.write(bi, "bmp", f);
        } catch (IOException ignored) {
        }
    }

    public static void save(BufferedImage bi) {
        save(bi, "save");
    }

}
