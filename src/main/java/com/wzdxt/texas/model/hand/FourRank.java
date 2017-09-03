package com.wzdxt.texas.model.hand;

import com.wzdxt.texas.Constants;
import com.wzdxt.texas.model.Card;
import com.wzdxt.texas.model.CardSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by wzdxt on 2017/8/27.
 */
public class FourRank extends AbsHand {
    protected int rank1; // single

    protected FourRank(Collection<Card> c) {
        super(c);
    }

    @Override
    public int getSort() {
        return 7;
    }

    @Override
    public long getValue() {
        return rank * Constants.TOTAL_RANK + rank1;
    }

    public static FourRank of(Card c1, Collection<Card> c) {
        FourRank fr = new FourRank(c);
        fr.rank = c1.getRank();
        fr.add(c1);
        if (fr.size() != 5) {
            throw new IllegalArgumentException();
        }
        return fr;
    }

    public static FourRank compose(Collection<Card> c) {
        int[] rankCnt = new int[Constants.TOTAL_RANK];
        c.forEach(card -> rankCnt[card.getRank()]++);
        int rank4 = -1;
        for (int i = 0; i < rankCnt.length; i++) {
            int cnt = rankCnt[i];
            switch (cnt) {
                case 0:
                case 1:
                    break;
                case 4:
                    rank4 = i;
                    break;
                default:
                    return null;
            }
        }
        if (rank4 == -1) {
            return null;
        }
        for (Card card : c) {
            if (card.getRank() == rank4) {
                return FourRank.of(card, c);
            }
        }
        return null;
    }

    /**
     *
     * @param c can receive any size
     * @return
     */
    public static FourRank compose7(CardSet c) {
        Map<Integer, CardSet> allCardSet = new HashMap<>(13);
        Card large = null;
        for (Iterator<Card> iter = c.descendingIterator(); iter.hasNext(); ) {
            Card card = iter.next();
            CardSet set = allCardSet.computeIfAbsent(card.getRank(), a -> new CardSet());
            set.add(card);
            if (set.size() == 4) {
                if (large.getRank() == card.getRank()) {
                    large = null;
                }
                if (large == null) {
                    large = iter.next();
                }
                Card sample = set.first();
                CardSet fin = new CardSet(set);
                fin.add(large);
                return FourRank.of(sample, fin);
            }
            if (large == null) {
                large = card;
            }
        }
        return null;
    }

}
