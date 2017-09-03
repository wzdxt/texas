package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class OnePairTest {

    @Test
    public void compose7() throws Exception {
        assertNull(OnePair.compose7(CardSetTest.flush()));
        assertNotNull(OnePair.compose7(CardSetTest.four()));
        assertNotNull(OnePair.compose7(CardSetTest.fullHouse()));
        assertNull(OnePair.compose7(CardSetTest.highCard()));
        assertNotNull(OnePair.compose7(CardSetTest.oneP()));
        assertNull(OnePair.compose7(CardSetTest.royalFlush()));
        assertNull(OnePair.compose7(CardSetTest.straight()));
        assertNull(OnePair.compose7(CardSetTest.straight2()));
        assertNull(OnePair.compose7(CardSetTest.straightFlush()));
        assertNull(OnePair.compose7(CardSetTest.straightFlush2()));
        assertNotNull(OnePair.compose7(CardSetTest.threeR()));
        assertNotNull(OnePair.compose7(CardSetTest.twoP()));
    }

}