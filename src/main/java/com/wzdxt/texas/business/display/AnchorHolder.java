package com.wzdxt.texas.business.display;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by dai_x on 17-9-6.
 */
@Slf4j
public class AnchorHolder {
    static BufferedImage anchor;

    static {
        try {
            anchor = ImageIO.read(AnchorHolder.class.getResourceAsStream("/static/anchor.bmp"));
        } catch (IOException e) {
            log.error("{}", e.toString(), e);
        }
    }

    static BufferedImage getAnchor() {
        return anchor;
    }
}
