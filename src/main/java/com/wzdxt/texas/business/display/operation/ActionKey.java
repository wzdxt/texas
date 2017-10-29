package com.wzdxt.texas.business.display.operation;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class ActionKey extends AbsAction {
    int key;

    @Override
    public ActionKey set(int[] p) {
        this.set(p[0]);
        return this;
    }

    public ActionKey set(int k) {
        this.key = k;
        return this;
    }

    @Override
    public boolean doPerform() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.keyPress(key);
        robot.keyRelease(key);
        return true;
    }

}
