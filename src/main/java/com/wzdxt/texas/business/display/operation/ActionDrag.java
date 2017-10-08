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
public class ActionDrag extends AbsAction {
    int x1, y1, x2, y2;

    @Override
    public ActionDrag set(int[] p) {
        this.set(p[0], p[1], p[2], p[3]);
        return this;
    }

    public ActionDrag set(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        return this;
    }

    @Override
    public boolean doPerform() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        robot.mouseMove(screenParam.getGameX1() + x1, screenParam.getGameY1() + y1);
        robot.mousePress(BUTTON1_DOWN_MASK);
        Thread.sleep(100);
        robot.mouseMove(screenParam.getGameX1() + x2, screenParam.getGameY1() + y2);
        robot.mouseRelease(BUTTON1_DOWN_MASK);
        return true;
    }

    public static void main(String[] args) {
        new ActionDrag().set(800, 300, 800, 330).perform();
    }
}
