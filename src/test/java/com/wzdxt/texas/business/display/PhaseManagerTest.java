package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.GameWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhaseManagerTest extends ScreenTestBase {
    @Autowired
    AnchorMatcher anchorMatcher;
    @Autowired
    PhaseManager phaseManager;
    @Autowired
    Displayer displayer;
    @Autowired
    GameWindow window;

    @Test
    public void testAnchor() throws Exception {
        switchTo("anchor.bmp");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void testCurrentPhaseMainPage() throws Exception {
        switchTo("1.PNG");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void testCurrentPhaseWaiting() throws Exception {
        switchTo("2.PNG");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.WAITING);
    }

    @Test
    public void testTotalCoin() throws Exception {
        switchTo("1.PNG");
        String s = phaseManager.getTotalCoinOcrRes();
        assertEquals("7.02万", s);
        int coin = phaseManager.getTotalCoin();
        assertEquals(70200, coin);
    }

    @Test
    public void testPlayerPool() throws Exception {
        switchTo("2.png");
        int[] pools = phaseManager.getPlayerPool();
        assertEquals(75, pools[1]);
        assertEquals(600, pools[2]);
        assertEquals(600, pools[3]);
        assertEquals(150, pools[4]);
        assertEquals(600, pools[5]);
    }

    @Test
    public void testPlayerRemain() throws Exception {
        switchTo("2.png");
        boolean[] remains = phaseManager.getPlayerRemain();
        assertEquals(false, remains[1]);
        assertEquals(true, remains[2]);
        assertEquals(true, remains[3]);
        assertEquals(true, remains[4]);
        assertEquals(true, remains[5]);
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/screen/" + s).toURI());
    }

}

