package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.ImageCutter;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.business.display.operation.AbsCheck;
import com.wzdxt.texas.business.display.operation.CheckPoint;
import com.wzdxt.texas.business.display.util.OcrUtil;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/9.
 */
@Component
public class PhaseManager {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private GameWindow screen;
    @Autowired
    private ApplicationContext cxt;
    @Autowired
    private ImageCutter imageCutter;

    public GameStatus.Phase getCurrentPhase() {
        for (DisplayerConfigure.Phase phase : configure.getPhase().values()) {
            List<AbsCheck> checkList = AbsCheck.fromConfig(cxt, phase.getCheck());
            boolean succ = true;
            for (AbsCheck check : checkList) {
                succ &= check.perform(1);
            }
            if (succ) return GameStatus.Phase.of(phase.getName());
        }

        return GameStatus.Phase.NONE;
    }

    public GameStatus.Status getCurrentStatus() {
        return GameStatus.Status.MY_TURN;
    }

    protected String getTotalCoinOcrRes() {
        int[] area = configure.getOcrArea().getTotalCoin();
        return ocr(area[0], area[1], area[2], area[3]);
    }

    public int getTotalCoin() {
        String s = getTotalCoinOcrRes();
        return str2int(s);
    }

    public int[] getPlayerPool() {
        int[] ret = new int[6];
        for (int i = 0; i < 6; i++) {
            int[] area = configure.getOcrArea().getPlayerPool()[i];
            String s= ocr(area[0], area[1], area[2], area[3]);
            ret[i] = s == null ? 0 : str2int(s);
        }
        return ret;
    }

    public boolean[] getPlayerRemain() {
        boolean[] ret = new boolean[6];
        int rgb = configure.getOcrArea().getPlayerRemainColor();
        for (int i = 0; i < 6; i++) {
            int[] point = configure.getOcrArea().getPlayerRemain()[i];
            ret[i] = cxt.getBean(CheckPoint.class).set(point[0], point[1], rgb).perform(1);
        }
        return ret;
    }

    protected int str2int(String s) {
        s = s.replaceAll("\\s", "");
        int len = s.length();
        int multi = 1;
        if (len == 0) return 0;
        switch (s.charAt(len - 1)) {
            case '万':
                multi = 1_0000;
                s = s.substring(0, len - 1);
                break;
            case '亿':
                multi = 1_0000_0000;
                s = s.substring(0, len - 1);
                break;
        }
        double d = Double.valueOf(s);
        return (int)Math.round(d * multi);
    }

    protected String ocr(int x1, int y1, int x2, int y2) {
        BufferedImage bi = screen.capture(x1, y1, x2, y2);
        bi = imageCutter.cutEdge(bi);
        return bi == null ? null : OcrUtil.recognize(bi);
    }

}
