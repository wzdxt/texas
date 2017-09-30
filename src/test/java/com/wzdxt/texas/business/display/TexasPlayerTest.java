package com.wzdxt.texas.business.display;

import com.wzdxt.texas.TestBase;
import com.wzdxt.texas.business.master.MasterDecision;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-30.
 */
public class TexasPlayerTest extends TestBase {

    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    private TexasPlayer player;

    @Test
    public void makeActionNoCall() throws Exception {
        test(150, 10000, 0, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 0, MasterDecision.CALL_2, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 0, MasterDecision.CALL_5, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 0, MasterDecision.CALL_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 0, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 0, MasterDecision.BET_2_5, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 0, MasterDecision.BET_2_10, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 0, MasterDecision.BET_5_25, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 0, MasterDecision.BET_10_50, TexasPlayer.FinalAction.BET_10);
        test(150, 10000, 0, MasterDecision.BET_25_50, TexasPlayer.FinalAction.BET_25);
        test(150, 10000, 0, MasterDecision.ALL_IN, TexasPlayer.FinalAction.ALL_IN);
    }

    @Test
    public void makeActionHasCall() throws Exception {
        test(150, 10000, 150, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.FOLD);
        test(150, 10000, 150, MasterDecision.CALL_2, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 150, MasterDecision.CALL_5, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 150, MasterDecision.CALL_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 150, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 150, MasterDecision.BET_2_5, TexasPlayer.FinalAction.RAISE_DOUBLE);
        test(150, 10000, 150, MasterDecision.BET_2_10, TexasPlayer.FinalAction.RAISE_DOUBLE);
        test(150, 10000, 150, MasterDecision.BET_5_25, TexasPlayer.FinalAction.BET_5);
        test(150, 10000, 150, MasterDecision.BET_10_50, TexasPlayer.FinalAction.BET_10);
        test(150, 10000, 150, MasterDecision.BET_25_50, TexasPlayer.FinalAction.BET_25);
        test(150, 10000, 150, MasterDecision.ALL_IN, TexasPlayer.FinalAction.ALL_IN);
    }

    @Test
    public void makeActionCallLMy() throws Exception {
        test(150, 1000, 1500, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.FOLD);
        test(150, 1000, 1500, MasterDecision.CALL_2, TexasPlayer.FinalAction.FOLD);
        test(150, 1000, 1500, MasterDecision.CALL_5, TexasPlayer.FinalAction.FOLD);
        test(150, 1000, 1500, MasterDecision.CALL_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.BET_2_5, TexasPlayer.FinalAction.FOLD);
        test(150, 1000, 1500, MasterDecision.BET_2_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.BET_5_25, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.BET_10_50, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.BET_25_50, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 1000, 1500, MasterDecision.ALL_IN, TexasPlayer.FinalAction.CHECK_OR_CALL);
    }

    @Test
    public void makeActionCallMiddle() throws Exception {
        test(150, 10000, 1500, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.FOLD);
        test(150, 10000, 1500, MasterDecision.CALL_2, TexasPlayer.FinalAction.FOLD);
        test(150, 10000, 1500, MasterDecision.CALL_5, TexasPlayer.FinalAction.FOLD);
        test(150, 10000, 1500, MasterDecision.CALL_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 1500, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 1500, MasterDecision.BET_2_5, TexasPlayer.FinalAction.FOLD);
        test(150, 10000, 1500, MasterDecision.BET_2_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 1500, MasterDecision.BET_5_25, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 1500, MasterDecision.BET_10_50, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test(150, 10000, 1500, MasterDecision.BET_25_50, TexasPlayer.FinalAction.BET_25);
        test(150, 10000, 1500, MasterDecision.ALL_IN, TexasPlayer.FinalAction.ALL_IN);
    }

    @Test
    public void makeActionCallMiddle75() throws Exception {
        test75(150, 10000, 1425, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.FOLD);
        test75(150, 10000, 1425, MasterDecision.CALL_2, TexasPlayer.FinalAction.FOLD);
        test75(150, 10000, 1425, MasterDecision.CALL_5, TexasPlayer.FinalAction.FOLD);
        test75(150, 10000, 1425, MasterDecision.CALL_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test75(150, 10000, 1425, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test75(150, 10000, 1425, MasterDecision.BET_2_5, TexasPlayer.FinalAction.FOLD);
        test75(150, 10000, 1425, MasterDecision.BET_2_10, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test75(150, 10000, 1425, MasterDecision.BET_5_25, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test75(150, 10000, 1425, MasterDecision.BET_10_50, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test75(150, 10000, 1425, MasterDecision.BET_25_50, TexasPlayer.FinalAction.BET_25);
        test75(150, 10000, 1425, MasterDecision.ALL_IN, TexasPlayer.FinalAction.ALL_IN);
    }

    @Test
    public void makeActionCallMiddle750() throws Exception {
        test750(150, 10000, 1500, MasterDecision.CHECK_OR_FOLD, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.CALL_2, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.CALL_5, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.CALL_10, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.CALL_ANY, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test750(150, 10000, 1500, MasterDecision.BET_2_5, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.BET_2_10, TexasPlayer.FinalAction.FOLD);
        test750(150, 10000, 1500, MasterDecision.BET_5_25, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test750(150, 10000, 1500, MasterDecision.BET_10_50, TexasPlayer.FinalAction.CHECK_OR_CALL);
        test750(150, 10000, 1500, MasterDecision.BET_25_50, TexasPlayer.FinalAction.RAISE_DOUBLE);
        test750(150, 10000, 1500, MasterDecision.ALL_IN, TexasPlayer.FinalAction.ALL_IN);
    }

    private void test(int bb, int my, int call, MasterDecision decision, TexasPlayer.FinalAction action) throws Exception {
        GameStatus status = new GameStatus();
        status.setBigBlind(bb);
        status.setMyCoin(my);
        status.setCallNeed(call);
        status.setPlayerPools(new int[]{0, 0, 0, 0, 0, 0});
        collector.checkThat(player.makeAction(decision, status), equalTo(action));
    }

    private void test75(int bb, int my, int call, MasterDecision decision, TexasPlayer.FinalAction action) throws Exception {
        GameStatus status = new GameStatus();
        status.setBigBlind(bb);
        status.setMyCoin(my);
        status.setCallNeed(call);
        status.setPlayerPools(new int[]{75, 0, 0, 0, 0, 0});
        collector.checkThat(player.makeAction(decision, status), equalTo(action));
    }

    private void test750(int bb, int my, int call, MasterDecision decision, TexasPlayer.FinalAction action) throws Exception {
        GameStatus status = new GameStatus();
        status.setBigBlind(bb);
        status.setMyCoin(my);
        status.setCallNeed(call);
        status.setPlayerPools(new int[]{750, 0, 0, 0, 0, 0});
        collector.checkThat(player.makeAction(decision, status), equalTo(action));
    }

}