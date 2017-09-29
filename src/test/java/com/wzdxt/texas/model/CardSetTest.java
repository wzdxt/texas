package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.TestBase;
import com.wzdxt.texas.util.Tuple;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Arrays;
import java.util.Iterator;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by wzdxt on 2017/9/2.
 */
public class CardSetTest extends TestBase {

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Test
    public void uniform() throws Exception {
        testDiffRank(new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 0},
                new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 0});
        testDiffRank(new byte[]{2, 3}, new byte[]{2, 3, 0, 1, 0},
                new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 2});
        testDiffRank(new byte[]{2, 2}, new byte[]{2, 3, 0, 1, 0},
                new byte[]{0, 0}, new byte[]{0, 1, 2, 3, 2});
        testDiffRank(new byte[]{2, 2}, new byte[]{2, 3, 1, 1, 1},
                new byte[]{0, 0}, new byte[]{0, 1, 2, 2, 2});

        testSameRank(new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 0},
                new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 0});
        testSameRank(new byte[]{2, 3}, new byte[]{2, 3, 0, 1, 0},
                new byte[]{0, 1}, new byte[]{0, 1, 2, 3, 2});
        testSameRank(new byte[]{2, 3}, new byte[]{3, 3, 0, 1, 0},
                new byte[]{0, 1}, new byte[]{0, 0, 2, 3, 2});
        testSameRank(new byte[]{2, 3}, new byte[]{2, 2, 0, 1, 0},
                new byte[]{0, 1}, new byte[]{0, 0, 2, 3, 2});
        testSameRank(new byte[]{2, 3}, new byte[]{3, 2, 3, 1, 2},
                new byte[]{0, 1}, new byte[]{0, 1, 0, 2, 1});
    }

    public void testSameRank(byte[] myS, byte[] commonS, byte[] myT, byte[] commonT) throws Exception {
        test(myS, commonS, myT, commonT, true);
    }

    public void testDiffRank(byte[] myS, byte[] commonS, byte[] myT, byte[] commonT) throws Exception {
        test(myS, commonS, myT, commonT, false);
    }

    public void test(byte[] myS, byte[] commonS, byte[] myT, byte[] commonT, boolean same) throws Exception {
        MyCard my = new MyCard();
        CommonCard common = new CommonCard();
        my.add(Card.of(myS[0], Constants.RANK_2));
        my.add(Card.of(myS[1], same ? Constants.RANK_2 : Constants.RANK_3));
        common.add(Card.of(commonS[0], Constants.RANK_4));
        common.add(Card.of(commonS[1], Constants.RANK_5));
        common.add(Card.of(commonS[2], Constants.RANK_6));
        common.add(Card.of(commonS[3], Constants.RANK_7));
        common.add(Card.of(commonS[4], Constants.RANK_8));
        Tuple<MyCard, CommonCard> t = CardSet.uniform(my, common);
        my = t.left;
        common = t.right;
        Iterator<Card> iter;
        iter = my.iterator();
        collector.checkThat(iter.next().getSuit(), equalTo(myT[0]));
        collector.checkThat(iter.next().getSuit(), equalTo(myT[1]));
        iter = common.iterator();
        collector.checkThat(iter.next().getSuit(), equalTo(commonT[0]));
        collector.checkThat(iter.next().getSuit(), equalTo(commonT[1]));
        collector.checkThat(iter.next().getSuit(), equalTo(commonT[2]));
        collector.checkThat(iter.next().getSuit(), equalTo(commonT[3]));
        collector.checkThat(iter.next().getSuit(), equalTo(commonT[4]));
    }

    public static CardSet royalFlush() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 1, Constants.RANK_9),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 1, Constants.RANK_J),
                Card.of((byte) 1, Constants.RANK_Q),
                Card.of((byte) 1, Constants.RANK_K),
                Card.of((byte) 1, Constants.RANK_A)
        ));
    }

    public static CardSet flush() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_9),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 1, Constants.RANK_J),
                Card.of((byte) 1, Constants.RANK_Q),
                Card.of((byte) 1, Constants.RANK_K),
                Card.of((byte) 2, Constants.RANK_A)
        ));
    }

    public static CardSet four() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_8),
                Card.of((byte) 3, Constants.RANK_8),
                Card.of((byte) 2, Constants.RANK_8),
                Card.of((byte) 1, Constants.RANK_Q),
                Card.of((byte) 0, Constants.RANK_Q),
                Card.of((byte) 2, Constants.RANK_Q)
        ));
    }

    public static CardSet fullHouse() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_8),
                Card.of((byte) 2, Constants.RANK_8),
                Card.of((byte) 1, Constants.RANK_Q),
                Card.of((byte) 0, Constants.RANK_Q),
                Card.of((byte) 2, Constants.RANK_Q)
        ));
    }

    public static CardSet highCard() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_2),
                Card.of((byte) 2, Constants.RANK_K),
                Card.of((byte) 1, Constants.RANK_3),
                Card.of((byte) 0, Constants.RANK_J),
                Card.of((byte) 2, Constants.RANK_Q)
        ));
    }

    public static CardSet oneP() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_2),
                Card.of((byte) 2, Constants.RANK_K),
                Card.of((byte) 1, Constants.RANK_2),
                Card.of((byte) 0, Constants.RANK_J),
                Card.of((byte) 2, Constants.RANK_Q)
        ));
    }

    public static CardSet straight() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_2),
                Card.of((byte) 2, Constants.RANK_4),
                Card.of((byte) 1, Constants.RANK_3),
                Card.of((byte) 0, Constants.RANK_J),
                Card.of((byte) 2, Constants.RANK_5)
        ));
    }

    public static CardSet straight2() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_Q),
                Card.of((byte) 2, Constants.RANK_4),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 0, Constants.RANK_J),
                Card.of((byte) 2, Constants.RANK_9)
        ));
    }

    public static CardSet straightFlush() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 3, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_2),
                Card.of((byte) 3, Constants.RANK_4),
                Card.of((byte) 3, Constants.RANK_3),
                Card.of((byte) 0, Constants.RANK_J),
                Card.of((byte) 3, Constants.RANK_5)
        ));
    }

    public static CardSet straightFlush2() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 1, Constants.RANK_Q),
                Card.of((byte) 2, Constants.RANK_4),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 1, Constants.RANK_J),
                Card.of((byte) 1, Constants.RANK_9)
        ));
    }

    public static CardSet threeR() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_4),
                Card.of((byte) 2, Constants.RANK_4),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 0, Constants.RANK_4),
                Card.of((byte) 2, Constants.RANK_9)
        ));
    }

    public static CardSet twoP() {
        return new CardSet(Arrays.asList(
                Card.of((byte) 1, Constants.RANK_8),
                Card.of((byte) 0, Constants.RANK_A),
                Card.of((byte) 3, Constants.RANK_8),
                Card.of((byte) 2, Constants.RANK_4),
                Card.of((byte) 1, Constants.RANK_10),
                Card.of((byte) 0, Constants.RANK_10),
                Card.of((byte) 0, Constants.RANK_4)
        ));
    }


}