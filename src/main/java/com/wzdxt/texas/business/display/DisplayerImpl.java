package com.wzdxt.texas.business.display;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dai_x on 17-9-4.
 */
@org.springframework.stereotype.Component
public class DisplayerImpl implements Displayer {
    @Override
    public GameStatus getCurrentStatus() {
        screenCapture();
        return null;
    }

    BufferedImage screenCapture() {
        try {
            BufferedImage screenCapture = new Robot().createScreenCapture(
                    new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())
            );
            screenCapture.getRGB(1,1);
            return screenCapture;
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }
}
