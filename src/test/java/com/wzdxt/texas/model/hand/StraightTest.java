package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class StraightTest {

    @Test
    public void compose7() throws Exception {
        assertNotNull(Straight.compose7(CardSetTest.flush()));
        assertNull(Straight.compose7(CardSetTest.four()));
        assertNull(Straight.compose7(CardSetTest.fullHouse()));
        assertNull(Straight.compose7(CardSetTest.highCard()));
        assertNull(Straight.compose7(CardSetTest.oneP()));
        assertNotNull(Straight.compose7(CardSetTest.royalFlush()));
        assertNotNull(Straight.compose7(CardSetTest.straight()));
        assertNotNull(Straight.compose7(CardSetTest.straight2()));
        assertNotNull(Straight.compose7(CardSetTest.straightFlush()));
        assertNotNull(Straight.compose7(CardSetTest.straightFlush2()));
        assertNull(Straight.compose7(CardSetTest.threeR()));
        assertNull(Straight.compose7(CardSetTest.twoP()));
    }


}