package com.wzdxt.texas.business.display.operation;

import com.wzdxt.texas.business.display.logic.GameWindow;
import com.wzdxt.texas.config.DisplayerConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by dai_x on 17-9-13.
 */
@Component
public class OperationEngine {
    @Autowired
    private GameWindow window;
    @Autowired
    private ApplicationContext ctx;

    public void execute(DisplayerConfigure.OperationPlan operationPlan) throws OperationException {
        int checkFail = 0;
        boolean redo = false;
        for (int i = 0; i < operationPlan.size(); ) {
            DisplayerConfigure.OperationPlanItem planItem = operationPlan.get(i);
            boolean res = execute(planItem);
            if (res) {
                i++;
                if (planItem.getCheck() != null && !redo) {
                    checkFail = 0;
                }
                redo = false;
            } else {
                if (++checkFail >= 3) {
                    throw new OperationException(operationPlan, i);
                }
                if (i > 0) {  // 退回重新操作
                    i--;
                    if (i > 0) {
                        redo = true;
                        i--;        // 退两步，重新check后重做
                    }
                }
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ignored) {
                }
            }
        }
    }

    public boolean execute(DisplayerConfigure.OperationPlanItem planItem) {
        return planItem.getCheck() != null ? execute(planItem.getCheck()) : execute(planItem.getAct());
    }

    private boolean execute(DisplayerConfigure.ActionOperationList actOperationList) {
        List<AbsAction> actionList = AbsAction.fromConfig(ctx, actOperationList);
        for (AbsAction action : actionList) {
            action.perform();
        }
        ActionMove move = ctx.getBean(ActionMove.class);
        move.set(10, 10);
        move.perform();
        return true;
    }

    private boolean execute(DisplayerConfigure.CheckOperationList checkOperationList) {
        window.refresh();
        List<AbsCheck> checkList = AbsCheck.fromConfig(ctx, checkOperationList);
        for (AbsCheck check : checkList) {
            if (!check.perform(1))
                return false;
        }
        return true;
    }

    public static class OperationException extends Exception {
        DisplayerConfigure.OperationPlan operationPlan;
        int step;

        OperationException(DisplayerConfigure.OperationPlan operationPlan, int step) {
            this.operationPlan = operationPlan;
            this.step = step;
        }

        @Override
        public String toString() {
            return String.format("[%d]", step) + super.toString();
        }
    }

}
