package com.wzdxt.texas.business.master;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dai_x on 17-9-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlopTurnTest extends TexasMasterTestBase {

    @Test
    public void suggestAA() throws Exception {
//        test("♠A♥A", "♣A♦K♦3♥Q", MasterDecision.ALL_IN);
//        test("♠A♥A", "♣K♦K♦3♥Q", MasterDecision.BET_25_50);
        test("♠A♥A", "♣2♦2♦3♥4", MasterDecision.BET_25_50);
//        test("♠A♥A", "♣4♦2♦3♥6", MasterDecision.CALL_5);
//        test("♠A♥A", "♣7♦9♦8♥6", MasterDecision.CALL_5);
//        test("♠A♥A", "♣3♦8♦J♥9", MasterDecision.BET_25_50);
    }

    @Test
    public void suggest44() throws Exception {
        test("♠4♥4", "", MasterDecision.CALL_2);
        test("♠4♥4", "♣A♦K♦3♥", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥4", "♣3♦K♦3♥", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥4", "♣K♦K♦3♥", MasterDecision.CALL_2);
        test("♠4♥4", "♣K♦K♦10♥", MasterDecision.CHECK_OR_FOLD);
    }

    @Test
    public void suggest1010() throws Exception {
        test("♠10♥10", "", MasterDecision.BET_2_10);
        test("♠10♥10", "♣A♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠10♥10", "♣A♦K♦Q", MasterDecision.CALL_2);
        test("♠10♥10", "♣3♦K♦3", MasterDecision.CALL_2);
        test("♠10♥10", "♣K♦K♦3", MasterDecision.BET_2_10);
        test("♠10♥10", "♣K♦K♦10", MasterDecision.CALL_ANY);
        test("♠10♥10", "♣5♦4♦J", MasterDecision.CALL_2);
    }

    @Test
    public void suggestQA() throws Exception {
        test("♠Q♥A", "", MasterDecision.BET_2_10);
        test("♠Q♥A", "♣A♦K♦3", MasterDecision.CALL_ANY);
        test("♠Q♥A", "♣A♦K♦Q", MasterDecision.CALL_ANY);
        test("♠Q♥A", "♣3♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠Q♥A", "♣K♦K♦3", MasterDecision.CALL_2);
        test("♠Q♥A", "♣K♦K♦10", MasterDecision.CALL_5);
        test("♠Q♥A", "♣5♦4♦J", MasterDecision.CALL_2);
        test("♠Q♥A", "♣8♦6♦K", MasterDecision.CHECK_OR_FOLD);
    }

    @Test
    public void suggest49() throws Exception {
        test("♠4♥9", "", MasterDecision.CALL_2);
        test("♠4♥9", "♣A♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥9", "♣3♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥9", "♣K♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥9", "♣K♦K♦10", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥9", "♣5♦4♦J", MasterDecision.CALL_2);
        test("♠4♥9", "♣8♦6♦K", MasterDecision.CHECK_OR_FOLD);
    }

}