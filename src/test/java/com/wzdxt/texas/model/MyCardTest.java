package com.wzdxt.texas.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dai_x on 17-9-30.
 */
public class MyCardTest {

    @Test
    public void test() throws Exception {
        MyCard myCard = new MyCard();
        myCard.add(Card.of((byte)0, (byte) 0));
        myCard.add(Card.of((byte)3, (byte) 0));
        MyCard myCard1 = (MyCard) myCard.clone();
        CommonCard commonCard = new CommonCard();
        MyCard myCard2 = CardSet.uniform(myCard1, commonCard).left;
        assertNotEquals(myCard1, myCard2);
    }

    @Test
    public void test2() throws Exception {
        CardSet set1 = new CardSet();
        set1.add(Card.of((byte)0, (byte) 0));
        CardSet set2 = new CardSet();
        set2.add(Card.of((byte)3, (byte) 0));
        assertNotEquals(set1, set2);
    }

}