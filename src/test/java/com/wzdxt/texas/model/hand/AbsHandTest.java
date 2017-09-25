package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSet;
import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-25.
 */
public class AbsHandTest {
    @Test
    public void of() throws Exception {
        CardSet set;
        set = CardSetTest.royalFlush();
        RoyalFlush rf = RoyalFlush.compose7(set);
        assertEquals(rf, AbsHand.of(rf.getId()));
    }

}