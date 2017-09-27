package com.wzdxt.texas.business.master;

import com.wzdxt.texas.TexasApplication;
import com.wzdxt.texas.config.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by dai_x on 17-9-19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Config.class)
public class MasterTest extends TexasMasterTestBase {

    @Test
    public void test() throws Exception {
        test("♠Q♥9", "♣2♦Q♦9", MasterDecision.CALL_ANY);
        test("♠Q♥9", "♣2♦Q♦9♣7", MasterDecision.BET_25_50);
        test("♠Q♥9", "♣2♦Q♦9♣7♥7", MasterDecision.BET_5_25);
    }

}