package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.operation.OperationEngine;
import com.wzdxt.texas.business.master.MasterDecision;
import com.wzdxt.texas.business.master.TexasMaster;
import com.wzdxt.texas.config.DisplayerConfigure;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dai_x on 17-9-15.
 */
@Component
public class TexasPlayer {
    @Autowired
    private TexasMaster master;
    @Autowired
    private DisplayerConfigure configure;
    @Autowired
    private OperationEngine operationEngine;

    public MasterDecision askMaster(GameStatus status) {
        return master.suggest(status.getMyCard(), status.getCommonCard());
    }

    public FinalAction makeAction(MasterDecision masterDecision, GameStatus status) {
        return null;
    }

    public void act(FinalAction action) throws OperationEngine.OperationException {
        DisplayerConfigure.OperationPlan plan = configure.getOperation().get(action.value);
        operationEngine.execute(plan);
    }


    @AllArgsConstructor
    public enum FinalAction{
        FOLD("fold"),
        CHECK_OR_CALL("check_or_call"),
        RAISE_DOUBLE("raise"),
        ALL_IN("all_in"),
        BET_5("bet_5"),
        BET_10("bet_10"),
        BET_25("bet_25"),
        BET_50("bet_50");

        public String value;
    }
}
