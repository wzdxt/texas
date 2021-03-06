package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.ScreenParam;
import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.config.DisplayerConfigure;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzdxt on 2017/9/5.
 */
@NoArgsConstructor
public abstract class AbsAction implements Operation {
    @Autowired
    protected DisplayerConfigure configure;
    @Autowired
    protected ScreenParam screenParam;

    @Override
    public boolean perform() {
        try {
            return this.doPerform();
        } catch (AWTException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    abstract boolean doPerform() throws AWTException, InterruptedException;

    @Override
    abstract public AbsAction set(int[] p);

    public static List<AbsAction> fromConfig(ApplicationContext cxt, DisplayerConfigure.ActionOperationList actionGroup) {
        List<AbsAction> list = new ArrayList<>();
        for (DisplayerConfigure.ActionOperation operation : actionGroup) {
            AbsAction action = null;
            if (operation.move != null) {
                action = cxt.getBean(ActionMove.class);
                action.set(operation.move);
            } else if (operation.click != null) {
                action = cxt.getBean(ActionClick.class);
                action.set(operation.click);
            } else if (operation.drag != null) {
                action = cxt.getBean(ActionDrag.class);
                action.set(operation.drag);
            } else if (operation.key != 0) {
                action = cxt.getBean(ActionKey.class);
                ((ActionKey)action).set(operation.key);
            }
            if (action != null) list.add(action);
        }
        return list;
    }

}
