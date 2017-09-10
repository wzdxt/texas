package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;

/**
 * Created by wzdxt on 2017/9/5.
 */
@Component
@Scope("prototype")
public class ActionClick extends AbsAction {
    int x, y;

    @Override
    public void set(int[] p) {
        this.set(p[0], p[1]);
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean doPerform() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.mousePress(BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        return true;
    }

}
