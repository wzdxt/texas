package com.wzdxt.texas.service;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.CardSetTest;
import com.wzdxt.texas.model.hand.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class ComposerTest {
    @Test
    public void largerHandsAfterRiver1() throws Exception {
        List<CardSet> list = new Composer().largerHandsAfterRiver(Arrays.asList(
                Card.of((byte)0, Constants.RANK_Q),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)0, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_9),
                Card.of((byte)0, Constants.RANK_8)
        ), Arrays.asList(
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)0, Constants.RANK_K)
        ));
        assertEquals(0, list.size());
    }

    @Test
    public void largerHandsAfterRiver2() throws Exception {
        List<CardSet> list = new Composer().largerHandsAfterRiver(Arrays.asList(
                Card.of((byte)0, Constants.RANK_Q),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)0, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_9),
                Card.of((byte)0, Constants.RANK_8)
        ), Arrays.asList(
                Card.of((byte)1, Constants.RANK_A),
                Card.of((byte)1, Constants.RANK_K)
        ));
//        list.forEach(System.out::println);
        assertEquals(44, list.size());
    }

    @Test
    public void largerHandsAfterRiver3() throws Exception {
        List<CardSet> list = new Composer().largerHandsAfterRiver(Arrays.asList(
                Card.of((byte)0, Constants.RANK_7),
                Card.of((byte)1, Constants.RANK_7),
                Card.of((byte)2, Constants.RANK_K),
                Card.of((byte)3, Constants.RANK_A),
                Card.of((byte)0, Constants.RANK_Q)
        ), Arrays.asList(
                Card.of((byte)3, Constants.RANK_7),
                Card.of((byte)1, Constants.RANK_Q)
        ));
//        list.forEach(System.out::println);
        assertEquals(13, list.size());
    }

    @Test
    public void composeHand() throws Exception {
        Composer composer = new Composer();
        assertTrue(composer.composeHand(CardSetTest.flush()) instanceof Flush);
        assertTrue(composer.composeHand(CardSetTest.four()) instanceof FourRank);
        assertTrue(composer.composeHand(CardSetTest.fullHouse()) instanceof FullHouse);
        assertTrue(composer.composeHand(CardSetTest.highCard()) instanceof HighCard);
        assertTrue(composer.composeHand(CardSetTest.oneP()) instanceof OnePair);
        assertTrue(composer.composeHand(CardSetTest.royalFlush()) instanceof RoyalFlush);
        assertTrue(composer.composeHand(CardSetTest.straight()) instanceof Straight);
        assertTrue(composer.composeHand(CardSetTest.straight2()) instanceof Straight);
        assertTrue(composer.composeHand(CardSetTest.straightFlush()) instanceof StraightFlush);
        assertTrue(composer.composeHand(CardSetTest.straightFlush2()) instanceof StraightFlush);
        assertTrue(composer.composeHand(CardSetTest.threeR()) instanceof ThreeRank);
        assertTrue(composer.composeHand(CardSetTest.twoP()) instanceof TwoPair);
    }

}
