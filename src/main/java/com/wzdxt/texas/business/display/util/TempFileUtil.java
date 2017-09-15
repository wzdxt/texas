package com.wzdxt.texas.business.display.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by wzdxt on 2017/9/10.
 */
@Slf4j
public class TempFileUtil {
    public static Object withTempFile(BufferedImage image, Function<String, Object> function) {
        String path = "temp/temp-file/" + System.currentTimeMillis() + ".bmp";
        try {
            if (!ImageIO.write(image, "bmp", new File(path))) {
                throw new RuntimeException();
            }
            return function.apply(path);
        } catch (IOException e) {
            log.warn("fail to delete temp file " + path, e);
        } finally {
            File f = new File(path);
            if (f.exists()) f.delete();
        }
        return null;
    }
}
