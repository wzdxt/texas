package com.wzdxt.texas.business.display;

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
        return OcrUtil.recognize(bi);
    }

    public int getTotalCoin() {
        return 0;
    }


}
