package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSetTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/8/28.
 */
public class StraightFlushTest {
    @Test
    public void compose() throws Exception {
        assertNotNull(StraightFlush.compose(cardList1()));
        assertNotNull(StraightFlush.compose(cardList2()));
        assertNotNull(StraightFlush.compose(cardList3()));
        assertNotNull(StraightFlush.compose(cardList4()));
        assertNotNull(StraightFlush.compose(cardList5()));
        assertNotNull(StraightFlush.compose(cardList6()));
        assertNull(StraightFlush.compose(cardListNull3()));
    }

    @Test
    public void compose7() throws Exception {
        assertNull(StraightFlush.compose7(CardSetTest.flush()));
        assertNull(StraightFlush.compose7(CardSetTest.four()));
        assertNull(StraightFlush.compose7(CardSetTest.fullHouse()));
        assertNull(StraightFlush.compose7(CardSetTest.highCard()));
        assertNull(StraightFlush.compose7(CardSetTest.oneP()));
        assertNotNull(StraightFlush.compose7(CardSetTest.royalFlush()));
        assertNull(StraightFlush.compose7(CardSetTest.straight()));
        assertNull(StraightFlush.compose7(CardSetTest.straight2()));
        assertNotNull(StraightFlush.compose7(CardSetTest.straightFlush()));
        assertNotNull(StraightFlush.compose7(CardSetTest.straightFlush2()));
        assertNull(StraightFlush.compose7(CardSetTest.threeR()));
        assertNull(StraightFlush.compose7(CardSetTest.twoP()));
    }

    private List<Card> cardList1() {
        return Arrays.asList(
                Card.of((byte)0, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)0, Constants.RANK_Q),
                Card.of((byte)0, Constants.RANK_K),
                Card.of((byte)0, Constants.RANK_9)
        );
    }

    private List<Card> cardList2() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_A),
                Card.of((byte)1, Constants.RANK_2),
                Card.of((byte)1, Constants.RANK_3),
                Card.of((byte)1, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_5)
        );
    }

    private List<Card> cardList3() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_6),
                Card.of((byte)1, Constants.RANK_2),
                Card.of((byte)1, Constants.RANK_3),
                Card.of((byte)1, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_5)
        );
    }

    private List<Card> cardList4() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_6),
                Card.of((byte)1, Constants.RANK_7),
                Card.of((byte)1, Constants.RANK_3),
                Card.of((byte)1, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_5)
        );
    }

    private List<Card> cardList5() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_A),
                Card.of((byte)1, Constants.RANK_K),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)1, Constants.RANK_J),
                Card.of((byte)1, Constants.RANK_10)
        );
    }

    private List<Card> cardList6() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)1, Constants.RANK_J),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)1, Constants.RANK_K),
                Card.of((byte)1, Constants.RANK_A)
        );
    }

    private List<Card> cardListNull3() {
        return Arrays.asList(
                Card.of((byte)1, Constants.RANK_5),
                Card.of((byte)0, Constants.RANK_8),
                Card.of((byte)2, Constants.RANK_9),
                Card.of((byte)3, Constants.RANK_6),
                Card.of((byte)0, Constants.RANK_7)
        );
    }

}