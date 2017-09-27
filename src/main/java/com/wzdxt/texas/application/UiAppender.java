package com.wzdxt.texas.application;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Setter;

/**
 * Created by wzdxt on 2017/9/12.
 */
@Setter
public class UiAppender extends AppenderBase<ILoggingEvent> {
    private MainFrame mainFrame;

    private static UiAppender instance;

    public UiAppender() {
        int i = 1;
    }

    @Override
    public void start() {
        super.start();
        UiAppender.instance = this;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (mainFrame != null)
            mainFrame.addLog(event);
    }

    public static UiAppender getInstance() {
        return UiAppender.instance;
    }
}
