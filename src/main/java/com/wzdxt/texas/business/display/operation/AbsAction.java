package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
public abstract class AbsAction implements Operation {
    DisplayerConfigure configure;

    AbsAction(DisplayerConfigure configure) {
        this.configure = configure;
    }

    @Override
    public boolean perform() {
        try {
            return this.doPerform();
        } catch (AWTException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    abstract boolean doPerform() throws AWTException, InterruptedException;

    public static List<AbsAction> fromConfig(DisplayerConfigure configure, ScreenParam screenParam, DisplayerConfigure.ActionGroup actionGroup) {
        List<AbsAction> list = new ArrayList<>();
        for (DisplayerConfigure.ActionOperation operation : actionGroup) {
            if (operation.move != null) {
                list.add(new ActionMove(configure,
                        screenParam.getGameX1() + operation.move[0], screenParam.getGameY1() + operation.move[1]));
            } else if (operation.click != null) {
                list.add(new ActionClick(configure,
                        screenParam.getGameX1() + operation.click[0], screenParam.getGameY1() + operation.click[1]));
            }
        }
        return list;
    }

}
