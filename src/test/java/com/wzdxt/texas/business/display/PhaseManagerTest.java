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
        assertEquals("7.02万", phaseManager.getTotalCoinOcrRes());
        assertEquals(70200, phaseManager.getTotalCoin());
    }

    @Test
    public void test2PNG() throws Exception {
        switchTo("2.PNG");
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.WAITING);
        assertArrayEquals(new int[]{0, 75, 600, 600, 150, 600}, phaseManager.getPlayerPool());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerRemain());
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
    public void test7PNG() throws Exception {
        switchTo("7.PNG");
        assertEquals(Arrays.asList(Card.of("♠8"), Card.of("♠10")), phaseManager.getMyCard());
    }

    @Test
    public void test8PNG() throws Exception {
        switchTo("8.PNG");
//        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerExist());
//        assertEquals(8275, status.getMyCoin());
//        assertEquals(Arrays.asList(Card.of("♠3"), Card.of("♠10")), phaseManager.getMyCard());
        assertEquals(Arrays.asList(Card.of("♠Q"), Card.of("♣4"), Card.of("♥Q")), phaseManager.getCommonCard());
    }

    @Test
    public void test9PNG() throws Exception {
        switchTo("9.PNG");
//        assertArrayEquals(new boolean[]{true, true, true, true, true, false}, status.getPlayerExist());
        assertEquals(0, phaseManager.getCallNeed());
    }

    @Test
    public void test11PNG() throws Exception {
        switchTo("11.PNG");
        assertEquals(Arrays.asList(Card.of("♠K"), Card.of("♠7")), phaseManager.getMyCard());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/screen/" + s).toURI());
    }

}

