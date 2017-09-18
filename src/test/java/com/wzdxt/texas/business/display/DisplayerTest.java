package com.wzdxt.texas.business.display;

import com.wzdxt.texas.TestUtil;
import com.wzdxt.texas.model.Card;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-5.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DisplayerTest extends ScreenTestBase {
    @Autowired
    Displayer displayer;
    @Autowired
    ScreenParam screenParam;

    //    @Test
    public void matchAnchor() throws Exception {
        displayer.matchAnchor();
        BufferedImage bi = new Robot().createScreenCapture(
                new Rectangle(screenParam.getGameX1(), screenParam.getGameY1(),
                        screenParam.getWidth(), screenParam.getHeight()));
        System.out.println(screenParam);
        TestUtil.save(bi);
    }

    @Test
    public void test2PNG() throws Exception {
        switchTo("2.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.WAITING, status.getPhase());
    }

    @Test
    public void test3PNG() throws Exception {
        switchTo("3.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.WAITING, status.getPhase());
    }

    @Test
    public void test4PNG() throws Exception {
        switchTo("4.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.PLAYING, status.getPhase());
        assertEquals(GameStatus.Status.WATCHING, status.getStatus());
    }

    @Test
    public void test5PNG() throws Exception {
        switchTo("5.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.PLAYING, status.getPhase());
        assertEquals(GameStatus.Status.MY_TURN, status.getStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerExist());
        assertEquals(150, status.getBigBlind());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerRemain());
        assertEquals(6, status.getRemainNum());
        assertEquals(0, status.getCurrentTurn());
        assertEquals(6275, status.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 0, 5375}, status.getPlayerPools());
        assertEquals(5375, status.getThisTurnPool());
        assertEquals(9100, status.getMyCoin());
        assertEquals(0, status.getMyPool());
        assertEquals(5375, status.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♣9"), Card.of("♦2")), status.getMyCard());
        assertEquals(Arrays.asList(Card.of("♥9"), Card.of("♦J"), Card.of("♥Q")), status.getCommonCard());
    }

    @Test
    public void test6PNG() throws Exception {
        switchTo("6.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.PLAYING, status.getPhase());
        assertEquals(GameStatus.Status.MY_TURN, status.getStatus());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerExist());
        assertEquals(150, status.getBigBlind());
        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerRemain());
        assertEquals(6, status.getRemainNum());
        assertEquals(0, status.getCurrentTurn());
        assertEquals(225, status.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 75, 150}, status.getPlayerPools());
        assertEquals(225, status.getThisTurnPool());
        assertEquals(9100, status.getMyCoin());
        assertEquals(0, status.getMyPool());
        assertEquals(150, status.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♠4"), Card.of("♣3")), status.getMyCard());
        assertEquals(Collections.emptyList(), status.getCommonCard());
    }

    @Test
    public void test8PNG() throws Exception {
        switchTo("8.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.PLAYING, status.getPhase());
        assertEquals(GameStatus.Status.MY_TURN, status.getStatus());
//        assertArrayEquals(new boolean[]{true, true, true, true, true, true}, status.getPlayerExist());
        assertEquals(150, status.getBigBlind());
        assertArrayEquals(new boolean[]{true, false, true, true, true, false}, status.getPlayerRemain());
        assertEquals(4, status.getRemainNum());
        assertEquals(0, status.getCurrentTurn());
        assertEquals(1500, status.getTotalPool());
        assertArrayEquals(new int[]{0, 0, 0, 0, 750, 0}, status.getPlayerPools());
        assertEquals(750, status.getThisTurnPool());
        assertEquals(8275, status.getMyCoin());
        assertEquals(0, status.getMyPool());
        assertEquals(750, status.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♠3"), Card.of("♠10")), status.getMyCard());
        assertEquals(Arrays.asList(Card.of("♠Q"), Card.of("♣4"), Card.of("♥Q")), status.getCommonCard());
    }

    @Test
    public void test9PNG() throws Exception {
        switchTo("9.PNG");
        GameStatus status = displayer.getGameStatus();
        assertEquals(GameStatus.Phase.PLAYING, status.getPhase());
        assertEquals(GameStatus.Status.MY_TURN, status.getStatus());
//        assertArrayEquals(new boolean[]{true, true, true, true, true, false}, status.getPlayerExist());
        assertEquals(150, status.getBigBlind());
        assertArrayEquals(new boolean[]{true, false, true, true, true, false}, status.getPlayerRemain());
        assertEquals(600, status.getTotalPool());
        assertArrayEquals(new int[]{150, 0, 150, 150, 150, 0}, status.getPlayerPools());
        assertEquals(8125, status.getMyCoin());
        assertEquals(0, status.getCallNeed());
        assertEquals(Arrays.asList(Card.of("♣Q"), Card.of("♠3")), status.getMyCard());
        assertEquals(Collections.emptyList(), status.getCommonCard());
    }

    protected void switchTo(String s) throws Exception {
        setScreen(this.getClass().getClassLoader().getResource("static/screen/" + s).toURI());
    }

}


