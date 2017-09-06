package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.config.DisplayerConfigure;

import java.awt.*;

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


}
