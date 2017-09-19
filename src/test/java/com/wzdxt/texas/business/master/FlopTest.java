package com.wzdxt.texas.business.master;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dai_x on 17-9-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlopTest extends TexasMasterTestBase {

    @Test
    public void suggestAA() throws Exception {
//        ♠ ♥ ♣ ♦
        test("♠A♥A", "", MasterDecision.BET_2_10);
        test("♠A♥A", "♣A♦K♦3", MasterDecision.CALL_ANY);
        test("♠A♥A", "♣K♦K♦3", MasterDecision.CALL_ANY);
        test("♠A♥A", "♣2♦2♦3", MasterDecision.CALL_ANY);
        test("♠A♥A", "♣4♦2♦3", MasterDecision.BET_2_10);
        test("♠A♥A", "♣7♦9♦8", MasterDecision.CALL_5);
        test("♠A♥A", "♣3♦8♦J", MasterDecision.CALL_ANY);
    }

    @Test
    public void suggest44() throws Exception {
        test("♠4♥4", "", MasterDecision.CALL_2);
        test("♠4♥4", "♣A♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥4", "♣3♦K♦3", MasterDecision.CHECK_OR_FOLD);
        test("♠4♥4", "♣K♦K♦3", MasterDecision.CALL_2);
        test("♠4♥4", "♣K♦K♦10", MasterDecision.CHECK_OR_FOLD);
    }

    @Test
    public void suggest1010() throws Exception {
        test("♠10♥10", "", MasterDecision.CALL_5);
        test("♠10♥10", "♣A♦K♦3", MasterDecision.CALL_5);
        test("♠10♥10", "♣3♦K♦3", MasterDecision.BET_2_5);
        test("♠10♥10", "♣K♦K♦3", MasterDecision.CALL_10);
        test("♠10♥10", "♣K♦K♦10", MasterDecision.BET_10_50);
        test("♠10♥10", "♣5♦4♦J", MasterDecision.CALL_5);
    }

}