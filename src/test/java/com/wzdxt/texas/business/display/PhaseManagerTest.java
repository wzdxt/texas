package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.Window;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhaseManagerTest {
    @Autowired
    AnchorMatcher anchorMatcher;
    @Autowired
    PhaseManager phaseManager;
    @Autowired
    Displayer displayer;
    @Autowired
    Window window;

    @Before
    public void setUp() throws Exception {
        System.out.println("switch to anchor");
        displayer.matchAnchor();
        window.refresh();
    }

    @Test
    public void getCurrentPhase() throws Exception {
        anchorMatcher.match(AnchorHolder.getAnchor());
        window.refresh();
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void getCurrentPhaseMainPage() throws Exception {
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(GameStatus.Phase.MAIN_PAGE, phase);
    }

    @Test
    public void getCurrentPhaseWaiting() throws Exception {
        switchTo("2.png");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(GameStatus.Phase.WAITING, phase);
    }

    @Test
    public void getTotalCoin() throws Exception {
        String s = phaseManager.getTotalCoinOcrRes();
        assertEquals("7.02万", s);
        int coin = phaseManager.getTotalCoin();
        assertEquals(70200, coin);
    }

    @Test
    public void getPlayerPool() throws Exception {
        switchTo("2.png");
        int[] pools = phaseManager.getPlayerPool();
        assertEquals(75, pools[0]);
        assertEquals(75, pools[1]);
        assertEquals(600, pools[2]);
        assertEquals(600, pools[3]);
        assertEquals(150, pools[4]);
        assertEquals(600, pools[5]);
    }

    protected void switchTo(String s) throws Exception {
        System.out.println("now switch to " + s);
        Thread.sleep(3000);
        window.refresh();
    }

}

