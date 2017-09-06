package com.wzdxt.texas.business.display;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dai_x on 17-9-6.
 */
public class AnchorHolder {
    static BufferedImage anchor;
    static {
        File f = new File(AnchorHolder.class.getClassLoader().getResource("static/anchor.bmp").getPath());
        try {
            anchor = ImageIO.read(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static BufferedImage getAnchor() {
        return anchor;
    }
}
