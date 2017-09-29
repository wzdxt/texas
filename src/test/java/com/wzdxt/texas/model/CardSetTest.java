package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class CardSetTest {

    public static CardSet royalFlush() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)1, Constants.RANK_9),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)1, Constants.RANK_J),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)1, Constants.RANK_K),
                Card.of((byte)1, Constants.RANK_A)
        ));
    }

    public static CardSet flush() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_9),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)1, Constants.RANK_J),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)1, Constants.RANK_K),
                Card.of((byte)2, Constants.RANK_A)
        ));
    }

    public static CardSet four() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_8),
                Card.of((byte)3, Constants.RANK_8),
                Card.of((byte)2, Constants.RANK_8),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)0, Constants.RANK_Q),
                Card.of((byte)2, Constants.RANK_Q)
        ));
    }

    public static CardSet fullHouse() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_8),
                Card.of((byte)2, Constants.RANK_8),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)0, Constants.RANK_Q),
                Card.of((byte)2, Constants.RANK_Q)
        ));
    }

    public static CardSet highCard() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_2),
                Card.of((byte)2, Constants.RANK_K),
                Card.of((byte)1, Constants.RANK_3),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)2, Constants.RANK_Q)
        ));
    }

    public static CardSet oneP() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_2),
                Card.of((byte)2, Constants.RANK_K),
                Card.of((byte)1, Constants.RANK_2),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)2, Constants.RANK_Q)
        ));
    }

    public static CardSet straight() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_2),
                Card.of((byte)2, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_3),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)2, Constants.RANK_5)
        ));
    }

    public static CardSet straight2() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_Q),
                Card.of((byte)2, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)2, Constants.RANK_9)
        ));
    }

    public static CardSet straightFlush() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)3, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_2),
                Card.of((byte)3, Constants.RANK_4),
                Card.of((byte)3, Constants.RANK_3),
                Card.of((byte)0, Constants.RANK_J),
                Card.of((byte)3, Constants.RANK_5)
        ));
    }

    public static CardSet straightFlush2() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)1, Constants.RANK_Q),
                Card.of((byte)2, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)1, Constants.RANK_J),
                Card.of((byte)1, Constants.RANK_9)
        ));
    }

    public static CardSet threeR() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_4),
                Card.of((byte)2, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_4),
                Card.of((byte)2, Constants.RANK_9)
        ));
    }

    public static CardSet twoP() {
        return new CardSet(Arrays.asList(
                Card.of((byte)1, Constants.RANK_8),
                Card.of((byte)0, Constants.RANK_A),
                Card.of((byte)3, Constants.RANK_8),
                Card.of((byte)2, Constants.RANK_4),
                Card.of((byte)1, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_10),
                Card.of((byte)0, Constants.RANK_4)
        ));
    }


}