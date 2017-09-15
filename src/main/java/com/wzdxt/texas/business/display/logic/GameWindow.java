package com.wzdxt.texas.business.display.logic;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.util.RobotUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by wzdxt on 2017/9/10.
 */
@Slf4j
@Component
public class GameWindow {
    @Autowired
    ScreenParam screenParam;
    @Autowired
    RobotUtil robotUtil;
    private BufferedImage bi;
    private static String name = String.valueOf(System.currentTimeMillis());

    public BufferedImage capture(int x1, int y1, int x2, int y2) {
        if (bi == null) refresh();
        return bi.getSubimage(x1, y1, x2 - x1, y2 - y1);
    }

    public BufferedImage refresh() {
        this.bi = robotUtil.screenCapture(
                screenParam.getGameX1(), screenParam.getGameY1(),
                screenParam.getGameX2(), screenParam.getGameY2());
        return bi;
    }

    public BufferedImage getScreenCapture() {
        return bi;
    }


    public void save(String s) {
        File f = new File(String.format("temp/image-save/%s/%s.bmp", name, s));
        try {
            ImageIO.write(bi, "bmp", f);
        } catch (IOException e) {
            log.error("save image fail. " + f.toString(), e);
        }
    }
}
