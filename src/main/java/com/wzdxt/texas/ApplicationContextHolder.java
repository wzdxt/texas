package com.wzdxt.texas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-25.
 */
@Component
public class ApplicationContextHolder {
    private static ApplicationContextHolder instance;

    @Autowired
    private ApplicationContext ctx;

    public ApplicationContextHolder() {
        instance = this;
    }

    public static ApplicationContext get() {
        return instance.ctx;
    }
}
