package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class ActionMove extends AbsAction {
    int x, y;

    public ActionMove(DisplayerConfigure configure, int x, int y) {
        super(configure);
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean doPerform() throws AWTException {
        new Robot().mouseMove(x, y);
        return true;
    }

    public static void main(String[] args) {
        new ActionMove(null, 100, 100).perform();
    }
}
