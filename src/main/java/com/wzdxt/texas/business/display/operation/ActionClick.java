package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;

import static java.awt.event.InputEvent.BUTTON1_DOWN_MASK;

/**
 * Created by wzdxt on 2017/9/5.
 */
public class ActionClick extends AbsAction {
    int x, y;

    public ActionClick(DisplayerConfigure configure, int x, int y) {
        super(configure);
        this.x = x;
        this.y = y;
    }

    @Override
    public void doPerform() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.mouseMove(x, y);
        robot.mousePress(BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
        Thread.sleep(100);
    }

    public static void main(String[] args) {
        new ActionClick(null, 100, 210).perform();
    }
}
