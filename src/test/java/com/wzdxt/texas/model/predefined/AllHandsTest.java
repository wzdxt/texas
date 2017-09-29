package com.wzdxt.texas.model.predefined;

import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class AllHandsTest {

    @Test
    public void test() {
        AllHands.init();
        assertEquals(AllHands.ROYAL_FLUSH_SIZE, AllHands.ROYAL_FLUSH.size());
        assertEquals(AllHands.STRAIGHT_FLUSH_SIZE, AllHands.STRAIGHT_FLUSH.size());
        assertEquals(AllHands.FOUR_RANK_SIZE, AllHands.FOUR_RANK.size());
        assertEquals(AllHands.FULL_HOUSE_SIZE, AllHands.FULL_HOUSE.size());
        assertEquals(AllHands.FLUSH_SIZE, AllHands.FLUSH.size());
        assertEquals(AllHands.STRAIGHT_SIZE, AllHands.STRAIGHT.size());
        assertEquals(AllHands.THREE_RANK_SIZE, AllHands.THREE_RANK.size());
        assertEquals(AllHands.TWO_PAIR_SIZE, AllHands.TWO_PAIR.size());
        assertEquals(AllHands.ONE_PAIR_SIZE, AllHands.ONE_PAIR.size());
    }

    @Test
    public void testProcess() {
        AllHands.STRAIGHT.clear();
        AllHands.process(new int[]{44, 41, 38, 35, 28});
        assertEquals(1, AllHands.STRAIGHT.size());
    }

    @Ignore
    @Test
    public void testRootProcess() {
        AllHands allHands = Mockito.mock(AllHands.class);
        Mockito.doNothing().when(allHands).process(new int[]{});
    }

}