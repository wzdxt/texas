package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class TwoPairTest {

    @Test
    public void compose7() throws Exception {
        assertNull(TwoPair.compose7(CardSetTest.flush()));
        assertNull(TwoPair.compose7(CardSetTest.four()));
//        assertNotNull(TwoPair.compose7(CardSetTest.fullHouse()));     // not adjusted
        assertNull(TwoPair.compose7(CardSetTest.highCard()));
        assertNull(TwoPair.compose7(CardSetTest.oneP()));
        assertNull(TwoPair.compose7(CardSetTest.royalFlush()));
        assertNull(TwoPair.compose7(CardSetTest.straight()));
        assertNull(TwoPair.compose7(CardSetTest.straight2()));
        assertNull(TwoPair.compose7(CardSetTest.straightFlush()));
        assertNull(TwoPair.compose7(CardSetTest.straightFlush2()));
        assertNull(TwoPair.compose7(CardSetTest.threeR()));
        assertNotNull(TwoPair.compose7(CardSetTest.twoP()));
    }

}