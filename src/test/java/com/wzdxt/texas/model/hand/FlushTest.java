package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.model.CardSetTest;
import com.wzdxt.texas.model.predefined.AllHands;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class FlushTest {
    @Test
    public void compose7() throws Exception {
        assertNotNull(Flush.compose7(CardSetTest.flush()));
        assertNull(Flush.compose7(CardSetTest.four()));
        assertNull(Flush.compose7(CardSetTest.fullHouse()));
        assertNull(Flush.compose7(CardSetTest.highCard()));
        assertNull(Flush.compose7(CardSetTest.oneP()));
        assertNotNull(Flush.compose7(CardSetTest.royalFlush()));
        assertNull(Flush.compose7(CardSetTest.straight()));
        assertNull(Flush.compose7(CardSetTest.straight2()));
        assertNotNull(Flush.compose7(CardSetTest.straightFlush()));
        assertNotNull(Flush.compose7(CardSetTest.straightFlush2()));
        assertNull(Flush.compose7(CardSetTest.threeR()));
        assertNull(Flush.compose7(CardSetTest.twoP()));
    }

    @Test
    public void testPredefined() throws Exception {
        AllHands.init();
        Map<Long, Integer> map = new HashMap<>();
        int idx = 0;
        for (Flush flush : AllHands.FLUSH) {
            long id = flush.getId();
            assertFalse(String.format("same of %d and %d: %s", map.get(id), idx, flush), map.containsKey(id));
            assertNotNull(String.format("%d is illegal", idx), Flush.compose(flush));
            map.put(id, idx++);
        }
        assertEquals(AllHands.FLUSH_SIZE, AllHands.FLUSH.size());
    }

}