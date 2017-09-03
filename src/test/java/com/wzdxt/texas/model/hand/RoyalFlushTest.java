package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class RoyalFlushTest {
    @Test
    public void of() throws Exception {
        List<Card> cardList = cardList1();
        RoyalFlush rf = RoyalFlush.of(cardList);
        assertEquals(9, rf.getSort());
        assertEquals(0, rf.getSuit());
        assertEquals(Constants.RANK_10, rf.getRank());
        assertEquals(0, rf.getValue());
    }

    @Test
    public void compose() throws Exception {
        List<Card> cardList = cardList1();
        assertNotNull(RoyalFlush.compose(cardList));
    }

    @Test
    public void compose7() throws Exception {
        assertNull(RoyalFlush.compose7(CardSetTest.flush()));
        assertNull(RoyalFlush.compose7(CardSetTest.four()));
        assertNull(RoyalFlush.compose7(CardSetTest.fullHouse()));
        assertNull(RoyalFlush.compose7(CardSetTest.highCard()));
        assertNull(RoyalFlush.compose7(CardSetTest.oneP()));
        assertNotNull(RoyalFlush.compose7(CardSetTest.royalFlush()));
        assertNull(RoyalFlush.compose7(CardSetTest.straight()));
        assertNull(RoyalFlush.compose7(CardSetTest.straight2()));
        assertNull(RoyalFlush.compose7(CardSetTest.straightFlush()));
        assertNull(RoyalFlush.compose7(CardSetTest.straightFlush2()));
        assertNull(RoyalFlush.compose7(CardSetTest.threeR()));
        assertNull(RoyalFlush.compose7(CardSetTest.twoP()));
    }

    private List<Card> cardList1() {
        return Arrays.asList(
                new Card(0, Constants.RANK_10),
                new Card(0, Constants.RANK_J),
                new Card(0, Constants.RANK_Q),
                new Card(0, Constants.RANK_K),
                new Card(0, Constants.RANK_A)
        );
    }

}