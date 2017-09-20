package com.wzdxt.texas.business.display;

import com.wzdxt.texas.model.Card;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by wzdxt on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PhaseManagerTest extends ScreenTestBase {
    @Rule
    public ErrorCollector collector = new ErrorCollector();
    @Autowired
    PhaseManager phaseManager;

    @Test
    public void testAnchor() throws Exception {
        setScreen(this.getClass().getResourceAsStream("static/anchor.bmp"));
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
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 5375}, phaseManager.getPlayerPool());
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
        collector.checkThat(phaseManager.getPlayerExist(), equalTo(new boolean[]{true, true, true, true, true, true}));
//        assertEquals(8275, phaseManager.getMyCoin());
//        assertEquals(Arrays.asList(Card.of("♠3"), Card.of("♠10")), phaseManager.getMyCard());
//        assertEquals(Arrays.asList(Card.of("♠Q"), Card.of("♣4"), Card.of("♥Q")), phaseManager.getCommonCard());
    }

    @Test
    public void test9PNG() throws Exception {
        switchTo("9.PNG");
//        assertArrayEquals(new boolean[]{true, true, true, true, true, false}, status.getPlayerExist());
        assertEquals(Arrays.asList(Card.of("♣Q"), Card.of("♠3")), phaseManager.getMyCard());
//        assertEquals(0, phaseManager.getCallNeed());
    }

    @Test
    public void test11PNG() throws Exception {
        switchTo("11.PNG");
        assertEquals(Arrays.asList(Card.of("♠K"), Card.of("♠7")), phaseManager.getMyCard());
    }

    @Test
    public void testR1() throws Exception {
        switchTo("r-1.bmp");
//        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
//        assertEquals(150, phaseManager.getBigBlind());
//        assertArrayEquals(new boolean[]{false, true, true, true, true, true}, phaseManager.getPlayerRemain());
//        assertEquals(3075, phaseManager.getTotalPool());
//        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
//        assertEquals(0, phaseManager.getMyCoin());
        assertEquals(Arrays.asList(Card.of("♦10"), Card.of("♥2"), Card.of("♣5")), phaseManager.getCommonCard());
    }

    @Test
    public void testR2() throws Exception {
        switchTo("r-2.bmp");
        assertEquals(Arrays.asList(Card.of("♥8"), Card.of("♠A"), Card.of("♦A"), Card.of("♦3")), phaseManager.getCommonCard());
    }

    @Test
    public void testR3() throws Exception {
        switchTo("r-3.bmp");
        assertEquals(Arrays.asList(Card.of("♠A"), Card.of("♣K"), Card.of("♠K")), phaseManager.getCommonCard());
    }

    @Test
    public void testR4() throws Exception {
        switchTo("r-4.bmp");
        assertEquals(Arrays.asList(Card.of("♣2"), Card.of("♦Q"), Card.of("♦5"), Card.of("♦2")), phaseManager.getCommonCard());
    }

    @Test
    public void testR6() throws Exception {
        switchTo("r-6.bmp");
        assertEquals(Arrays.asList(Card.of("♥7"), Card.of("♠Q"), Card.of("♠J"), Card.of("♦3"), Card.of("♦7")), phaseManager.getCommonCard());
    }

    @Test
    public void testR8() throws Exception {
        switchTo("r-8.bmp");
        assertEquals(Arrays.asList(Card.of("♦4"), Card.of("♦6"), Card.of("♦9"), Card.of("♦2"), Card.of("♦7")), phaseManager.getCommonCard());
    }

    @Test
    public void testR9() throws Exception {
        switchTo("r-9.bmp");
        assertEquals(Arrays.asList(Card.of("♥9"), Card.of("♠8"), Card.of("♠4")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♦8"), Card.of("♦5")), phaseManager.getMyCard());
    }

    @Test
    public void testR10() throws Exception {
        switchTo("r-10.bmp");
        assertEquals(Arrays.asList(Card.of("♣5"), Card.of("♠4")), phaseManager.getMyCard());
    }

    @Test
    public void testR11() throws Exception {
        switchTo("r-11.bmp");
        assertEquals(Arrays.asList(Card.of("♣9"), Card.of("♣J"), Card.of("♣A"), Card.of("♦A"), Card.of("♣4")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♥10"), Card.of("♠K")), phaseManager.getMyCard());
    }

    @Test
    public void testR13() throws Exception {
        switchTo("r-13.bmp");
        assertEquals(Arrays.asList(Card.of("♦5"), Card.of("♦8"), Card.of("♣A"), Card.of("♦A"), Card.of("♦Q")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♥3"), Card.of("♣Q")), phaseManager.getMyCard());
    }

    @Test
    public void testR14() throws Exception {
        switchTo("r-14.bmp");
        assertEquals(Arrays.asList(Card.of("♥6"), Card.of("♣K"), Card.of("♣2"), Card.of("♦3"), Card.of("♣7")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♠10"), Card.of("♦9")), phaseManager.getMyCard());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getResourceAsStream("/static/screen/" + s));
    }

}

