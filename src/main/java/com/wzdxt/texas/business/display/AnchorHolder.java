package com.wzdxt.texas.business.display;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dai_x on 17-9-6.
 */
@Slf4j
public class AnchorHolder {
    static BufferedImage anchor;
    static {
        File f = new File(AnchorHolder.class.getResource("/static/anchor.bmp").getPath());
        try {
            anchor = ImageIO.read(f);
        } catch (IOException e) {
            log.error("{}", e.toString(), e);
        }
    }

    static BufferedImage getAnchor() {
        return anchor;
    }
}
