package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class FourRankTest {

    @Test
    public void compose7() throws Exception {
        assertNull(FourRank.compose7(CardSetTest.flush()));
        assertNotNull(FourRank.compose7(CardSetTest.four()));
        assertNull(FourRank.compose7(CardSetTest.fullHouse()));
        assertNull(FourRank.compose7(CardSetTest.highCard()));
        assertNull(FourRank.compose7(CardSetTest.oneP()));
        assertNull(FourRank.compose7(CardSetTest.royalFlush()));
        assertNull(FourRank.compose7(CardSetTest.straight()));
        assertNull(FourRank.compose7(CardSetTest.straight2()));
        assertNull(FourRank.compose7(CardSetTest.straightFlush()));
        assertNull(FourRank.compose7(CardSetTest.straightFlush2()));
        assertNull(FourRank.compose7(CardSetTest.threeR()));
        assertNull(FourRank.compose7(CardSetTest.twoP()));
    }

}