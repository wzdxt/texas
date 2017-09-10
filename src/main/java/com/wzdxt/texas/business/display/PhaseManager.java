package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.ImageCutter;
import com.wzdxt.texas.business.display.logic.Window;
import com.wzdxt.texas.business.display.operation.AbsCheck;
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
    private Window screen;
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

    public String getTotalCoinOcrRes() {
        int[] area = configure.getOcrArea().getTotalCoin();
        BufferedImage bi = screen.capture(area[0], area[1], area[2], area[3]);
        bi = imageCutter.cutEdge(bi);
        return OcrUtil.recognize(bi);
    }

    public int getTotalCoin() {
        String s = getTotalCoinOcrRes();
        s = s.replaceAll("\\s", "");
        int len = s.length();
        int multi = 1;
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


}
