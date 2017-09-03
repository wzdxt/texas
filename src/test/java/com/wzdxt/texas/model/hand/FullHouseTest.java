package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class FullHouseTest {

    @Test
    public void compose7() throws Exception {
        assertNull(FullHouse.compose7(CardSetTest.flush()));
        assertNull(FullHouse.compose7(CardSetTest.four()));
        assertNotNull(FullHouse.compose7(CardSetTest.fullHouse()));
        assertNull(FullHouse.compose7(CardSetTest.highCard()));
        assertNull(FullHouse.compose7(CardSetTest.oneP()));
        assertNull(FullHouse.compose7(CardSetTest.royalFlush()));
        assertNull(FullHouse.compose7(CardSetTest.straight()));
        assertNull(FullHouse.compose7(CardSetTest.straight2()));
        assertNull(FullHouse.compose7(CardSetTest.straightFlush()));
        assertNull(FullHouse.compose7(CardSetTest.straightFlush2()));
        assertNull(FullHouse.compose7(CardSetTest.threeR()));
        assertNull(FullHouse.compose7(CardSetTest.twoP()));
    }

}