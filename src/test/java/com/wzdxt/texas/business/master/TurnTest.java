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
public class TurnTest extends TexasMasterTestBase {

    @Test
    public void suggestAA() throws Exception {
        test("♠A♥A", "♣A♦K♦3♥Q", MasterDecision.BET_25_50);
        test("♠A♥A", "♣K♦K♦3♥Q", MasterDecision.BET_10_50);
        test("♠A♥A", "♣2♦2♦3♥4", MasterDecision.BET_10_50);
        test("♠A♥A", "♣4♦2♦3♥6", MasterDecision.CALL_10);
        test("♠A♥A", "♣7♦9♦8♥6", MasterDecision.CALL_5);
        test("♠A♥A", "♣3♦8♦J♥9", MasterDecision.BET_10_50);
    }

    @Test
    public void suggest44() throws Exception {
        test("♠4♥4", "♣4♦K♦3♥A", MasterDecision.BET_25_50);
        test("♠4♥4", "♣3♦K♦3♥A", MasterDecision.CALL_5);
        test("♠4♥4", "♣K♦K♦3♥K", MasterDecision.BET_10_50);
        test("♠4♥4", "♣4♦K♦10♥K", MasterDecision.ALL_IN);
        test("♠4♥4", "♣K♦A♦3♥Q", MasterDecision.CALL_2);
    }

    @Test
    public void suggest1010() throws Exception {
        test("♠10♥10", "♣A♦K♦3♣J", MasterDecision.CALL_5);
        test("♠10♥10", "♣A♦K♦Q♣J", MasterDecision.ALL_IN);
        test("♠10♥10", "♣3♦K♦3♣J", MasterDecision.CALL_5);
        test("♠10♥10", "♣K♦K♦3♣J", MasterDecision.CALL_10);
        test("♠10♥10", "♣K♦K♦10♣9", MasterDecision.ALL_IN);
        test("♠10♥10", "♣5♦4♦J♣8", MasterDecision.CALL_10);
    }

    @Test
    public void suggestQA() throws Exception {
        test("♠Q♥A", "♣A♦K♦3♣J", MasterDecision.BET_10_50);
        test("♠Q♥A", "♣A♦K♦Q♣5", MasterDecision.BET_25_50);
        test("♠Q♥A", "♣A♦K♦Q♣10", MasterDecision.BET_10_50);
        test("♠Q♥A", "♣3♦K♦3♣10", MasterDecision.CHECK_OR_FOLD);    //5
        test("♠Q♥A", "♣K♦K♦3♣5", MasterDecision.CALL_2);
        test("♠Q♥A", "♣K♦K♦10♣J", MasterDecision.BET_25_50);
        test("♠Q♥A", "♣5♦4♦J♣9", MasterDecision.CHECK_OR_FOLD);     //5
        test("♠Q♥A", "♣8♦6♦K♣Q", MasterDecision.BET_2_10);
    }

    @Test
    public void suggest49() throws Exception {
//        test("♠4♥9", "", MasterDecision.CALL_2);
//        test("♠4♥9", "♣A♦K♦3", MasterDecision.CHECK_OR_FOLD);
//        test("♠4♥9", "♣3♦K♦3", MasterDecision.CHECK_OR_FOLD);
//        test("♠4♥9", "♣K♦K♦3", MasterDecision.CHECK_OR_FOLD);
//        test("♠4♥9", "♣K♦K♦10", MasterDecision.CHECK_OR_FOLD);
//        test("♠4♥9", "♣5♦4♦J", MasterDecision.CALL_2);
//        test("♠4♥9", "♣8♦6♦K", MasterDecision.CHECK_OR_FOLD);
    }

}