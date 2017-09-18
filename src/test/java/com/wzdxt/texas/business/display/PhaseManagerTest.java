package com.wzdxt.texas.business.display;

import com.wzdxt.texas.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhaseManagerTest extends ScreenTestBase {
    @Autowired
    PhaseManager phaseManager;

    @Test
    public void testAnchor() throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/anchor.bmp").toURI());
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void test1PNG() throws Exception {
        switchTo("1.PNG");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
        String s = phaseManager.getTotalCoinOcrRes();
        assertEquals("7.02万", s);
        int coin = phaseManager.getTotalCoin();
        assertEquals(70200, coin);
    }

    @Test
    public void test2PNG() throws Exception {
        switchTo("2.PNG");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.WAITING);
        int[] pools = phaseManager.getPlayerPool();
        assertEquals(75, pools[1]);
        assertEquals(600, pools[2]);
        assertEquals(600, pools[3]);
        assertEquals(150, pools[4]);
        assertEquals(600, pools[5]);
        boolean[] remains = phaseManager.getPlayerRemain();
        assertEquals(true, remains[1]);
        assertEquals(true, remains[2]);
        assertEquals(true, remains[3]);
        assertEquals(true, remains[4]);
        assertEquals(true, remains[5]);
    }

    @Test
    public void test5PNG() throws Exception {
        switchTo("5.PNG");
        assertEquals(150, phaseManager.getBigBlind());
        assertEquals(6275, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0,0,0,0,5375}, phaseManager.getPlayerPool());
        assertEquals(9100, phaseManager.getMyCoin());
        assertEquals(5375, phaseManager.getCallNeed());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(Arrays.asList(Card.of("♣9"), Card.of("♦2")), phaseManager.getMyCard());
        assertEquals(Arrays.asList(Card.of("♥9"), Card.of("♦J"), Card.of("♥Q")), phaseManager.getCommonCard());
    }

    @Test
    public void test8PNG() throws Exception {
        switchTo("8.PNG");
//        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerExist());
//        assertEquals(8275, status.getMyCoin());
//        assertEquals(Arrays.asList(Card.of("♠3"), Card.of("♠10")), phaseManager.getMyCard());
        assertEquals(Arrays.asList(Card.of("♠Q"), Card.of("♣4"), Card.of("♥Q")), phaseManager.getCommonCard());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/screen/" + s).toURI());
    }

}

