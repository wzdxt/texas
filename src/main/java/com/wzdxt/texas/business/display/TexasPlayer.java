package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.operation.OperationEngine;
import com.wzdxt.texas.business.master.MasterDecision;
import com.wzdxt.texas.business.master.TexasMaster;
import com.wzdxt.texas.config.DisplayerConfigure;
import com.wzdxt.texas.model.CommonCard;
import com.wzdxt.texas.model.MyCard;
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
        MasterDecision ret = master.suggest(new MyCard(status.getMyCard()), new CommonCard(status.getCommonCard()));
        if (ret == MasterDecision.ALL_IN)
            ret = MasterDecision.BET_25_50;
        return ret;
    }

    public FinalAction makeAction(MasterDecision masterDecision, GameStatus status) {
        // 1 decide min cost(call need) & max cost(my coin)
        // note: when call need == 0, may be direct_all_in!
        // 2 find minimal cost le decision.low, not overflow decision.high. find by
        // call
        // raise
        // 5 10 25 50
        int bb = status.getBigBlind();
        int low = masterDecision.getLow() * bb - status.getMyPool();
        int high = masterDecision.getHigh() * bb - status.getMyPool();
        int myCoin = status.getMyCoin();
        int callNeed = status.getCallNeed();
        if (callNeed == 0) {
            for (int playerPool : status.getPlayerPools()) {
                if (playerPool > status.getMyPool()) {
                    callNeed = myCoin;
                }
            }
        }
        // 2 start find
        if (callNeed > high) {
            return FinalAction.FOLD;
        }
        if (callNeed >= low || callNeed >= myCoin) {
            return FinalAction.CHECK_OR_CALL;
        }
        int myPool = status.getMyPool();
        int raise = (callNeed * 2 + myPool) / bb * bb - myPool;
        if (raise == 0) raise = bb;
        if (raise > myCoin) {
            return FinalAction.DIRECT_ALL_IN;
        }
        if (raise >= low) {
            return FinalAction.RAISE_DOUBLE;
        }
        int call5 = bb * 5;
        int call10 = bb * 10;
        int call25 = bb * 25;
        int call50 = bb * 50;
        if (call5 > myCoin) {
            return FinalAction.ALL_IN;
        } else if (call5 >= low) {
            return FinalAction.BET_5;
        } else if (call10 > myCoin) {
            return FinalAction.ALL_IN;
        } else if (call10 >= low) {
            return FinalAction.BET_10;
        } else if (call25 > myCoin) {
            return FinalAction.ALL_IN;
        } else if (call25 >= low) {
            return FinalAction.BET_25;
        } else if (call50 > myCoin) {
            return FinalAction.ALL_IN;
        } else if (call50 >= low) {
            return FinalAction.BET_50;
        } else {
            return FinalAction.ALL_IN;
        }
    }

    public void act(FinalAction action) throws OperationEngine.OperationException {
        DisplayerConfigure.OperationPlan plan = configure.getOperation().get(action.value);
        operationEngine.execute(plan);
    }


    @AllArgsConstructor
    public enum FinalAction {
        FOLD("fold"),
        CHECK_OR_CALL("check_or_call"),
        RAISE_DOUBLE("raise"),
        ALL_IN("all_in"),
        DIRECT_ALL_IN("direct_all_in"),
        BET_5("bet_5"),
        BET_10("bet_10"),
        BET_25("bet_25"),
        BET_50("bet_50");

        public String value;
    }
}
