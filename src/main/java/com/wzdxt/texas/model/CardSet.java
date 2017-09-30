package com.wzdxt.texas.model;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.util.Tuple;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by wzdxt on 2017/8/26.
 */
@EqualsAndHashCode(callSuper = true)
public class CardSet extends TreeSet<Card> {

    public CardSet(Collection<Card> c) {
        super(c);
    }

    public CardSet() {
        super();
    }


    /**
     * ONLY FOR 7 CARDS!!
     * @return byte[6] = 42 bit
     */
    public byte[] serialize() {
        long ret = 0;
        for (Card card : this) {
            ret  = (ret << 6) | card.getId();
        }
        BitSet bs = BitSet.valueOf(new long[]{ret});
        return bs.toByteArray();
    }

    /**
     * convert to uniform card set before calculate
     * <br> this will reduce total calculate case
     * @param myCard
     * @param commonCard
     * @return
     */
    public static Tuple<MyCard, CommonCard> uniform(MyCard myCard, CommonCard commonCard) {
        boolean[] isSuitUsed = new boolean[4];
        byte[] suitMap = new byte[4];
        Arrays.fill(suitMap, (byte) -1);
        MyCard newMy = new MyCard();
        for (Card card : myCard) {
            if (suitMap[card.getSuit()] >= 0) {
                newMy.add(Card.of(suitMap[card.getSuit()], card.getRank()));
            } else {
                for (byte targetSuit = 0; targetSuit < Constants.TOTAL_SUIT; targetSuit++) {
                    if (!isSuitUsed[targetSuit]) {
                        isSuitUsed[targetSuit] = true;
                        suitMap[card.getSuit()] = targetSuit;
                        newMy.add(Card.of(targetSuit, card.getRank()));
                        break;
                    }
                }
            }
        }

        boolean myCardRankIsSameAndConsiderSwap = false;
        Card my1, my2, newMy1 = null, newMy2 = null;
        Iterator<Card> iter = myCard.iterator();
        my1 = iter.next();
        my2 = iter.next();
        if (my1.getRank() == my2.getRank()) {
            myCardRankIsSameAndConsiderSwap = true;
            iter = newMy.iterator();
            newMy1 = iter.next();
            newMy2 = iter.next();
        }

        CommonCard newCommon = new CommonCard();
        for (Card card : commonCard) {
            if (myCardRankIsSameAndConsiderSwap) {
                if (card.getSuit() == my1.getSuit()) {
                    myCardRankIsSameAndConsiderSwap = false;
                } else if (card.getSuit() == my2.getSuit()) {
                    myCardRankIsSameAndConsiderSwap = false;
                    suitMap[my2.getSuit()] = newMy1.getSuit();
                    suitMap[my1.getSuit()] = newMy2.getSuit();
                }
            }
            if (suitMap[card.getSuit()] >= 0) {
                newCommon.add(Card.of(suitMap[card.getSuit()], card.getRank()));
            } else {
                for (byte targetSuit = 0; targetSuit < Constants.TOTAL_SUIT; targetSuit++) {
                    if (!isSuitUsed[targetSuit]) {
                        isSuitUsed[targetSuit] = true;
                        suitMap[card.getSuit()] = targetSuit;
                        newCommon.add(Card.of(targetSuit, card.getRank()));
                        break;
                    }
                }
            }
        }

        return Tuple.of(newMy, newCommon);
    }

}

