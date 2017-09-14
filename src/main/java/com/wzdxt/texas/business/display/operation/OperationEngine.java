package com.wzdxt.texas.business.display.operation;

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
    private ApplicationContext ctx;

    public void execute(DisplayerConfigure.OperationPlan operationPlan) throws OperationException {
        int checkFail = 0;
        for (int i = 0; i < operationPlan.size(); ) {
            DisplayerConfigure.OperationPlanItem planItem = operationPlan.get(i);
            boolean res = execute(planItem);
            if (res) {
                i++;
                if (planItem.getCheck() != null) {
                    checkFail = 0;
                }
            } else {
                if (++checkFail >= 3) {
                    throw new OperationException(operationPlan, i);
                }
                if (i > 0) {  // 退回重新操作
                    i--;
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
        return true;
    }

    private boolean execute(DisplayerConfigure.CheckOperationList checkOperationList) {
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
    }

}
