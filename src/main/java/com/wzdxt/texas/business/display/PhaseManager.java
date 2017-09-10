package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.operation.AbsCheck;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wzdxt on 2017/9/9.
 */
@Component
public class PhaseManager {
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private ScreenParam screenParam;

    public GameStatus.Phase getCurrentPhase() {
        for (DisplayerConfigure.Phase phase : configure.getPhase().values()) {
            List<AbsCheck> checkList = AbsCheck.fromConfig(configure, screenParam, phase.getCheck());
            boolean succ = true;
            for (AbsCheck check : checkList) {
                succ &= check.perform(1);
            }
            if (succ) return GameStatus.Phase.of(phase.getName());
        }

        return GameStatus.Phase.NONE;
    }

}
