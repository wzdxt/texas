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
        String path = "temp/image-save/"+name+".bmp";
        File f = new File(path);
        try {
            ImageIO.write(bi, "bmp", f);
        } catch (IOException ignored) {
        }
    }

    public static void save(BufferedImage bi) {
        save(bi, "save");
    }

}
