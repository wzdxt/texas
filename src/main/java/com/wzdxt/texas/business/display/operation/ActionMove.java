package com.wzdxt.texas.business.display.operation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class ActionMove extends AbsAction {
    int x, y;

    @Override
    public ActionMove set(int[] p) {
        this.set(p[0], p[1]);
        return this;
    }

    public ActionMove set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    @Override
    public boolean doPerform() throws AWTException {
        new Robot().mouseMove(x, y);
        return true;
    }

}
