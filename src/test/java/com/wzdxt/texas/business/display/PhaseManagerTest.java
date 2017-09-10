package com.wzdxt.texas.business.display;

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

    @Test
    public void getCurrentPhase() throws Exception {
        anchorMatcher.match(AnchorHolder.getAnchor());
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void getCurrentPhase2() throws Exception {
//        anchorMatcher.match(ImageIO.read(this.getClass().getResource("/static/train/1.PNG")));
        displayer.matchAnchor();
        System.out.println("now switch to 1.png");
        Thread.sleep(3000);
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(GameStatus.Phase.MAIN_PAGE, phase);
    }

}

