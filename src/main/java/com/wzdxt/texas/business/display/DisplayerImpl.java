package com.wzdxt.texas.business.display;

import com.wzdxt.texas.config.DisplayerConfigure;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by dai_x on 17-9-4.
 */
@Component
@Slf4j
public class DisplayerImpl implements Displayer {
    @Autowired
    private AnchorMatcher anchorMatcher;
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ScreenParam screenParam;

    private int screenWidth, screenHeight;

    public DisplayerImpl() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        screenWidth = size.width;
        screenHeight = size.height;
    }

    @Override
    public GameStatus getCurrentStatus() {
        screenCapture();
        return null;
    }

    BufferedImage screenCapture() {
        return screenCapture(0, 0, screenWidth, screenHeight);
    }

    BufferedImage screenCapture(int x1, int y1, int x2, int y2) {
        try {
            BufferedImage screenCapture = new Robot().createScreenCapture(
                    new Rectangle(x1, y1, x2 - x1, y2 - y1)
            );
            return screenCapture;
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    Point getMousePos() {
        return MouseInfo.getPointerInfo().getLocation();
    }

    Point getMousePosDelay() {
        int sec = configure.getAnchor().getDelay();
        try {
            System.out.println("starting get mouse position ...");
            for (int i = 0; i < sec; i++) {
                Thread.sleep(1000);
                System.out.println(sec - i);
            }
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
        return getMousePos();
    }

    @Cacheable("screenParam")
    public ScreenParam matchAnchor() {
        Point p = getMousePosDelay();
        int x1 = p.x;
        int y1 = p.y;
        int x2 = x1 + AnchorHolder.getAnchor().getWidth();
        int y2 = y1 + AnchorHolder.getAnchor().getHeight();
        x1 = Math.max(x1 - configure.getAnchor().getFix(), 0);
        y1 = Math.max(y1 - configure.getAnchor().getFix(), 0);
        x2 = Math.min(x2 + configure.getAnchor().getFix(), screenWidth);
        y2 = Math.min(y2 + configure.getAnchor().getFix(), screenHeight);
        BufferedImage screenCapture = screenCapture(x1, y1, x2, y2);
        AnchorMatcher.Result result = anchorMatcher.match(screenCapture);
        screenParam.setGameX1(x1 + result.x1);
        screenParam.setGameX2(x1 + result.x2);
        screenParam.setGameY1(y1 + result.y1);
        screenParam.setGameY2(y1 + result.y2);
        screenParam.setWidth(result.x2 - result.x1);
        screenParam.setHeight(result.y2 - result.y1);

        log.info(String.format("match anchor finished. mistake: %d, %s", result.getMistake(), screenParam));
        return screenParam;
    }

    void save(BufferedImage bi) {
        File f = new File("save.bmp");
        try {
            ImageIO.write(bi, "bmp", f);
        } catch (IOException ignored) {
        }
    }

}
