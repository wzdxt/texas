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
        Result result =
        int xTo = screen.getWidth() - configure.getAnchor().getWidth();
        int yTo = screen.getHeight() - configure.getAnchor().getHeight();
        for (int x1 = 0; x1 < xTo; x1++) {
            for (int y1 = 0; y1 < yTo; y1++) {

            }
        }
        return null;
    }
}
