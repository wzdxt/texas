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
        setScreen(this.getClass().getResourceAsStream("/static/anchor.bmp"));
        GameStatus.Phase phase = phaseManager.getCurrentPhase();
        assertEquals(phase, GameStatus.Phase.MAIN_PAGE);
    }

    @Test
    public void testR1() throws Exception {
        switchTo("r-1.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
//        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, true, true, true}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
//        assertEquals(2, phaseManager.getCurrentTurn());
        assertEquals(3075, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(0, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♦10"), Card.of("♥2"), Card.of("♣5")), phaseManager.getCommonCard());
    }

    @Test
    public void testR2() throws Exception {
        switchTo("r-2.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
//        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, true, true, true}, phaseManager.getPlayerRemain());
//        assertEquals(true, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(600, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(0, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♥8"), Card.of("♠A"), Card.of("♦A"), Card.of("♦3")), phaseManager.getCommonCard());
    }

    @Test
    public void testR3() throws Exception {
        switchTo("r-3.bmp");
//        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, true, true, true}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(750, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(9850, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♠A"), Card.of("♣K"), Card.of("♠K")), phaseManager.getCommonCard());
    }

    @Test
    public void testR4() throws Exception {
        switchTo("r-4.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, false, true, true, false}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(1950, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(9550, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♣2"), Card.of("♦Q"), Card.of("♦5"), Card.of("♦2")), phaseManager.getCommonCard());
    }

    @Test
    public void testR5() throws Exception {
        switchTo("r-5.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, false, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, false, true, true}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(375, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{150, 0, 0, 0, 75, 150}, phaseManager.getPlayerPool());
        assertEquals(9400, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
    }

    @Test
    public void testR6() throws Exception {
        switchTo("r-6.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, true, true, false}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(5025, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 1500, 0, 1500, 0}, phaseManager.getPlayerPool());
        assertEquals(23200, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♥7"), Card.of("♠Q"), Card.of("♠J"), Card.of("♦3"), Card.of("♦7")), phaseManager.getCommonCard());
    }

    @Test
    public void testR7() throws Exception {
        switchTo("r-7.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, false, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, false, true, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(750, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(23100, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
    }

    @Test
    public void testR8() throws Exception {
        switchTo("r-8.bmp");
        assertEquals(GameStatus.Phase.WAITING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
//        assertEquals(GameStatus.Status.WATCHING, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, false, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, false, true, true}, phaseManager.getPlayerRemain());
//        assertEquals(true, phaseManager.amILive());
//        assertEquals(5, phaseManager.getCurrentTurn());
        assertEquals(525, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
//        assertEquals(23100, phaseManager.getMyCoin());
//        assertEquals(5375, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♦4"), Card.of("♦6"), Card.of("♦9"), Card.of("♦2"), Card.of("♦7")), phaseManager.getCommonCard());
    }

    @Test
    public void testR9() throws Exception {
        switchTo("r-9.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, false, true, true, true}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(825, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(9850, phaseManager.getMyCoin());
        assertEquals(0, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♥9"), Card.of("♠8"), Card.of("♠4")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♦8"), Card.of("♦5")), phaseManager.getMyCard());
    }

    @Test
    public void testR10() throws Exception {
        switchTo("r-10.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, true, true, true}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(825, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 75, 150, 600}, phaseManager.getPlayerPool());
        assertEquals(9850, phaseManager.getMyCoin());
        assertEquals(600, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♣5"), Card.of("♠4")), phaseManager.getMyCard());
    }

    @Test
    public void testR11() throws Exception {
        switchTo("r-11.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, false, true, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(9925, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 3750, 0, 2275, 0}, phaseManager.getPlayerPool());
        assertEquals(8950, phaseManager.getMyCoin());
        assertEquals(3750, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♣9"), Card.of("♣J"), Card.of("♣A"), Card.of("♦A"), Card.of("♣4")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♥10"), Card.of("♠K")), phaseManager.getMyCard());
    }

    @Test
    public void testR12() throws Exception {
        switchTo("r-12.bmp");
        assertEquals(GameStatus.Phase.NONE, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, false, false, false, false}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
//        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(20500, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(8500, phaseManager.getMyCoin());
//        assertEquals(3750, phaseManager.getCallNeed());
    }

    @Test
    public void testR13() throws Exception {
        switchTo("r-13.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, true, true, false}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
        assertEquals(2, phaseManager.getCurrentTurn());
        assertEquals(600, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(8500, phaseManager.getMyCoin());
//        assertEquals(3750, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♦5"), Card.of("♦8"), Card.of("♣A"), Card.of("♦A"), Card.of("♦Q")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♥3"), Card.of("♣Q")), phaseManager.getMyCard());
    }

    @Test
    public void testR14() throws Exception {
        switchTo("r-14.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, false, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, true, true, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(600, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(8350, phaseManager.getMyCoin());
        assertEquals(0, phaseManager.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♥6"), Card.of("♣K"), Card.of("♣2"), Card.of("♦3"), Card.of("♣7")), phaseManager.getCommonCard());
        assertEquals(Arrays.asList(Card.of("♠10"), Card.of("♦9")), phaseManager.getMyCard());
    }

    @Test
    public void testR15() throws Exception {
        switchTo("r-15.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, false, false, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(2100, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 1500, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(8200, phaseManager.getMyCoin());
        assertEquals(1500, phaseManager.getCallNeed());
    }

    @Test
    public void testR16() throws Exception {
        switchTo("r-16.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, false, false, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, false, false, true}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
//        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(600, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(8050, phaseManager.getMyCoin());
//        assertEquals(1500, phaseManager.getCallNeed());
    }

    @Test
    public void testR17() throws Exception {
        switchTo("r-17.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, false}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, true, true, true, true, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(2, phaseManager.getCurrentTurn());
        assertEquals(750, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(7750, phaseManager.getMyCoin());
//        assertEquals(1500, phaseManager.getCallNeed());
    }

    @Test
    public void testR18() throws Exception {
        switchTo("r-18.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, false}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, false, true, false}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
        assertEquals(2, phaseManager.getCurrentTurn());
        assertEquals(2175, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 0}, phaseManager.getPlayerPool());
        assertEquals(7600, phaseManager.getMyCoin());
//        assertEquals(1500, phaseManager.getCallNeed());
    }

    @Test
    public void testR19() throws Exception {
        switchTo("r-19.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.FINISH, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, true, false, true}, phaseManager.getPlayerRemain());
        assertEquals(false, phaseManager.amILive());
        assertEquals(2, phaseManager.getCurrentTurn());
        assertEquals(1650, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 1050}, phaseManager.getPlayerPool());
        assertEquals(7450, phaseManager.getMyCoin());
//        assertEquals(1500, phaseManager.getCallNeed());
    }

    @Test
    public void testE1() throws Exception {
        switchTo("error/1.bmp");
        assertEquals(GameStatus.Phase.PLAYING, phaseManager.getCurrentPhase());
//        assertEquals(70200, phaseManager.getTotalCoin());
        assertEquals(GameStatus.Status.MY_TURN, phaseManager.getCurrentStatus());
        assertArrayEquals(new boolean[]{true, true, true, false, true, true}, phaseManager.getPlayerExist());
        assertEquals(150, phaseManager.getBigBlind());
        assertArrayEquals(new boolean[]{false, false, true, false, true, false}, phaseManager.getPlayerRemain());
        assertEquals(true, phaseManager.amILive());
        assertEquals(0, phaseManager.getCurrentTurn());
        assertEquals(900, phaseManager.getTotalPool());
        assertArrayEquals(new int[]{150, 0, 300, 0, 300, 150}, phaseManager.getPlayerPool());
        assertEquals(9700, phaseManager.getMyCoin());
        assertEquals(150, phaseManager.getCallNeed());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getResourceAsStream("/static/screen/" + s));
    }

}

