package com.wzdxt.texas.business.display;

import com.wzdxt.texas.business.display.logic.Window;
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

    @Test
    public void getCurrentPhase() throws Exception {
        anchorMatcher.match(AnchorHolder.getAnchor());
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void getCurrentPhaseMainPage() throws Exception {
        System.out.println("switch to anchor");
        displayer.matchAnchor();
        System.out.println("now switch to 1.png");
        Thread.sleep(3000);
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(GameStatus.Phase.MAIN_PAGE, phase);
    }

    @Test
    public void getCurrentPhaseWaiting() throws Exception {
        System.out.println("switch to anchor");
        displayer.matchAnchor();
        System.out.println("now switch to 2.png");
        Thread.sleep(3000);
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(GameStatus.Phase.WAITING, phase);
    }

    @Test
    public void getTotalCoin() throws Exception {
        System.out.println("switch to anchor");
        displayer.matchAnchor();
        window.refresh();
        String s = phaseManager.getTotalCoinOcrRes();
        assertEquals("7.02万", s);
        int coin = phaseManager.getTotalCoin();
        assertEquals(70200, coin);
    }

}
