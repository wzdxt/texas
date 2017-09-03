package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class ThreeRankTest {

    @Test
    public void compose7() throws Exception {
        assertNull(ThreeRank.compose7(CardSetTest.flush()));
        assertNotNull(ThreeRank.compose7(CardSetTest.four()));
        assertNotNull(ThreeRank.compose7(CardSetTest.fullHouse()));
        assertNull(ThreeRank.compose7(CardSetTest.highCard()));
        assertNull(ThreeRank.compose7(CardSetTest.oneP()));
        assertNull(ThreeRank.compose7(CardSetTest.royalFlush()));
        assertNull(ThreeRank.compose7(CardSetTest.straight()));
        assertNull(ThreeRank.compose7(CardSetTest.straight2()));
        assertNull(ThreeRank.compose7(CardSetTest.straightFlush()));
        assertNull(ThreeRank.compose7(CardSetTest.straightFlush2()));
        assertNotNull(ThreeRank.compose7(CardSetTest.threeR()));
        assertNull(ThreeRank.compose7(CardSetTest.twoP()));
    }

}