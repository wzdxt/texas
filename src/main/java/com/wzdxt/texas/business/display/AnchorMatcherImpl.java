package com.wzdxt.texas.business.display;

import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dai_x on 17-9-5.
 */
public class AnchorMatcherImpl implements AnchorMatcher {
    @Autowired
    private DisplayerConfigure configure;
    @Override
    public Result match(BufferedImage screen) {
        File f = new File("classpath:anchor.bmp");
        BufferedImage anchor = null;
        try {
            anchor = ImageIO.read(f);
        } catch (IOException ignored) {
        }
        int width = configure.getAnchor().getWidth();
        int height = configure.getAnchor().getHeight();
        Result result = new Result();
        result.setMistake(1e5);
        int xTo = screen.getWidth() - width;
        int yTo = screen.getHeight() - height;
        for (int x1 = 0; x1 < xTo; x1++) {
            for (int y1 = 0; y1 < yTo; y1++) {
                for (int i = x1; i < x1 + width; i++) {
                    for (int j = y1; j < y1 + height; j++) {

                    }
                }
            }
        }
        return null;
    }
}
